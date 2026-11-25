package net.hydra.jojomod.mixin.gravity.client;

import net.hydra.jojomod.access.ICamera;
import net.hydra.jojomod.util.RotationAnimation;
import net.hydra.jojomod.util.gravity.GravityAPI;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = Camera.class, priority = 1001)
public abstract class GravityCameraMixin {
    @Shadow private Entity entity;
    @Shadow @Final private Quaternionf rotation;
    @Shadow private float eyeHeightOld;
    @Shadow private float eyeHeight;
    @Shadow private float partialTickTime;

    @Unique
    private Entity roundabout$gravityCameraEntity() {
        Entity pov = ((ICamera) this).roundabout$getPovSwitch();
        return pov != null && pov.isAlive() && !pov.isRemoved() ? pov : this.entity;
    }

    // Adjust only the eye offset; native setup keeps camera events, zoom and collision handling.
    @ModifyArgs(
            method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setPosition(DDD)V")
    )
    private void roundabout$gravityEyeOffset(Args args) {
        Entity focused = roundabout$gravityCameraEntity();
        if (focused == null) return;
        RotationAnimation animation = GravityAPI.getRotationAnimation(focused);
        if (animation == null) return;
        Direction direction = GravityAPI.getGravityDirection(focused);
        long timeMs = focused.level().getGameTime() * 50L + (long) ((this.partialTickTime % 1.0F) * 50.0F);
        animation.update(timeMs);
        if (direction == Direction.DOWN && !animation.isInAnimation()) return;

        double eyeHeight = Mth.lerp(this.partialTickTime, this.eyeHeightOld, this.eyeHeight);
        Vec3 offset = animation.getEyeOffset(animation.getCurrentGravityRotation(direction, timeMs),
                new Vec3(0.0, eyeHeight, 0.0), direction);
        args.set(0, args.<Double>get(0) + offset.x);
        args.set(1, args.<Double>get(1) - eyeHeight + offset.y);
        args.set(2, args.<Double>get(2) + offset.z);
    }

    // Vanilla has the two-angle setter; NeoForge's three-angle setter also includes roll.
    // Only the setter that actually builds the quaternion contains this invocation.
    @Inject(
            method = "setRotation(FFF)V",
            at = @At(value = "INVOKE", target = "Lorg/joml/Quaternionf;rotationYXZ(FFF)Lorg/joml/Quaternionf;",
                    remap = false, shift = At.Shift.AFTER)
    )
    private void roundabout$gravityRotation(CallbackInfo ci) {
        Entity focused = roundabout$gravityCameraEntity();
        if (focused == null) return;
        RotationAnimation animation = GravityAPI.getRotationAnimation(focused);
        if (animation == null) return;
        Direction direction = GravityAPI.getGravityDirection(focused);
        long timeMs = focused.level().getGameTime() * 50L + (long) ((this.partialTickTime % 1.0F) * 50.0F);
        animation.update(timeMs);
        if (direction == Direction.DOWN && !animation.isInAnimation()) return;

        // GameRenderer 1.21.1 derives its view matrix from this quaternion's inverse.
        // Applying gravity again in renderLevel would rotate the world twice.
        Quaternionf gravityRotation = new Quaternionf(animation.getCurrentGravityRotation(direction, timeMs));
        this.rotation.set(gravityRotation.conjugate().mul(this.rotation));
        if (animation.isInAnimation()) Minecraft.getInstance().levelRenderer.needsUpdate();
    }
}
