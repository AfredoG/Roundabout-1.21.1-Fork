package net.hydra.jojomod.mixin.gravity.client;

import net.hydra.jojomod.util.gravity.GravityAPI;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemPickupParticle.class)
public abstract class GravityItemParticlePickupMixin {
    @Shadow @Final private Entity target;
    @Shadow private double targetX;
    @Shadow private double targetY;
    @Shadow private double targetZ;

    @Inject(method = "updatePosition", at = @At("TAIL"))
    private void roundabout$rotatePickupDestination(CallbackInfo ci) {
        if (GravityAPI.getGravityDirection(target) == Direction.DOWN) return;
        // 1.21 interpolates the destination between ticks. Use the same body/eye
        // midpoint as vanilla, with the gravity-aware eye position in all axes.
        Vec3 midpoint = target.position().add(target.getEyePosition()).scale(0.5);
        targetX = midpoint.x;
        targetY = midpoint.y;
        targetZ = midpoint.z;
    }
}
