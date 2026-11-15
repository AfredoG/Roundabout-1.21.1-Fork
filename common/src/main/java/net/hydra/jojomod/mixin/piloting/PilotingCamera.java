package net.hydra.jojomod.mixin.piloting;

import net.hydra.jojomod.access.ICamera;
import net.hydra.jojomod.access.IEntityAndData;
import net.hydra.jojomod.event.powers.TimeStop;
import net.hydra.jojomod.util.MainUtil;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class PilotingCamera implements ICamera {
    @Unique private Entity roundabout$povSwitch;
    @Unique private boolean roundabout$settingUpCamera;

    @Override
    public void roundabout$setEntity(Entity entity) {
        this.roundabout$povSwitch = entity;
    }

    @Override
    public Entity roundabout$getPovSwitch() {
        return this.roundabout$povSwitch;
    }

    @Unique
    private boolean roundabout$hasValidPov() {
        return this.roundabout$povSwitch != null
                && this.roundabout$povSwitch.isAlive() && !this.roundabout$povSwitch.isRemoved();
    }

    @Inject(method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
            at = @At("HEAD"), cancellable = true)
    private void roundabout$setup(BlockGetter level, Entity body, boolean detached, boolean mirrored,
                                 float partialTick, CallbackInfo ci) {
        if (roundabout$settingUpCamera || body == null) return;
        Entity focused = body;
        if (roundabout$hasValidPov() && !this.roundabout$povSwitch.is(body)) {
            focused = this.roundabout$povSwitch;
        } else {
            this.roundabout$povSwitch = null;
        }

        float cameraPartialTick = partialTick;
        if (((TimeStop) body.level()).CanTimeStopEntity(body) && !body.isPassenger()) {
            cameraPartialTick = ((IEntityAndData) body).roundabout$getPreTSTick();
        }
        if (focused == body && cameraPartialTick == partialTick) return;

        // Native setup handles NeoForge camera events, roll, collision, scale and partialTickTime.
        // Keep the body as the public camera entity after positioning at the controlled entity.
        roundabout$settingUpCamera = true;
        try {
            this.setup(level, focused, detached, mirrored, cameraPartialTick);
        } finally {
            this.entity = body;
            roundabout$settingUpCamera = false;
        }
        ci.cancel();
    }

    @Redirect(method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"))
    private float roundabout$controlledYaw(Entity focused, float partialTick) {
        if (!roundabout$hasValidPov() || focused != this.roundabout$povSwitch) {
            return focused.getViewYRot(partialTick);
        }
        return partialTick == 1.0F ? focused.getYRot()
                : MainUtil.controlledLerpAngleDegrees(partialTick % 1.0F, focused.yRotO, focused.getYRot(), 1);
    }

    @Redirect(method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"))
    private float roundabout$controlledPitch(Entity focused, float partialTick) {
        if (!roundabout$hasValidPov() || focused != this.roundabout$povSwitch) {
            return focused.getViewXRot(partialTick);
        }
        return partialTick == 1.0F ? focused.getXRot()
                : MainUtil.controlledLerpAngleDegrees(partialTick % 1.0F, focused.xRotO, focused.getXRot(), 1);
    }

    @Inject(method = "isDetached", at = @At("HEAD"), cancellable = true)
    private void roundabout$isDetached(CallbackInfoReturnable<Boolean> cir) {
        if (roundabout$hasValidPov()) cir.setReturnValue(true);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void roundabout$tick(CallbackInfo ci) {
        if (roundabout$hasValidPov() && (this.entity == null || !this.roundabout$povSwitch.is(this.entity))) {
            this.eyeHeightOld = this.eyeHeight;
            this.eyeHeight += (this.roundabout$povSwitch.getEyeHeight() - this.eyeHeight) * 0.5F;
            ci.cancel();
        }
    }

    @Shadow public abstract void setup(BlockGetter level, Entity entity, boolean detached, boolean mirrored, float partialTick);
    @Shadow private Entity entity;
    @Shadow private float eyeHeight;
    @Shadow private float eyeHeightOld;
}
