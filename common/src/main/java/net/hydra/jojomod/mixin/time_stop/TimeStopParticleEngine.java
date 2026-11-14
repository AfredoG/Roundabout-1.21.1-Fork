package net.hydra.jojomod.mixin.time_stop;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.access.IParticleAccess;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.event.powers.TimeStop;
import net.hydra.jojomod.mixin.access.AccessParticle;
import net.hydra.jojomod.util.config.ConfigManager;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class TimeStopParticleEngine {

    /**Freeze particle ticks and interpolation while retaining the NeoForge render pipeline.*/


    /**When a particle is created, mark it as created in a TS or not, particles made in a TS tick*/
    @ModifyVariable(
            method = "add(Lnet/minecraft/client/particle/Particle;)V", at = @At(value = "HEAD"))
    private Particle roundaboutMarkParticleTS(Particle $$0) {
        if ($$0 != null && level != null) {
            if (((TimeStop) level).inTimeStopRange(new Vec3i((int) ((AccessParticle) $$0).roundabout$getX(),
                    (int) ((AccessParticle) $$0).roundabout$getY(),
                    (int) ((AccessParticle) $$0).roundabout$getZ()))) {
                ((IParticleAccess) $$0).roundabout$setRoundaboutIsTimeStopCreated(true);
            }
        }
        return $$0;
    }

    @Inject(method = "tickParticle", at = @At("HEAD"), cancellable = true)
    void doNotTickParticleWhenTimeStopped(Particle particle, CallbackInfo ci) {
        AccessParticle particle1 = (AccessParticle) particle;
        if (!(particle instanceof ItemPickupParticle) && particle1 != null) {
            if (!((IParticleAccess) particle1).roundabout$getRoundaboutIsTimeStopCreated() && ((TimeStop) level).inTimeStopRange(new Vec3i((int) particle1.roundabout$getX(),
                    (int) particle1.roundabout$getY(),
                    (int) particle1.roundabout$getZ()))) {
                particle1.roundabout$setPrevX(particle1.roundabout$getX());
                particle1.roundabout$setPrevY(particle1.roundabout$getY());
                particle1.roundabout$setPrevZ(particle1.roundabout$getZ());
                ci.cancel();
            }
        }
    }

    // NeoForge renders particles in separate passes through this overload. Keep its
    // culling, render-type predicate and buffer lifecycle; only change particle time.
    @Inject(
            method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;Ljava/util/function/Predicate;)V",
            at = @At("HEAD"), cancellable = true, remap = false)
    private void roundabout$hidePresentParticlesDuringEpitaph(CallbackInfo ci) {
        if (ClientUtil.isUsingEpitaph()
                && !ConfigManager.getClientConfig().generalSettings.epitaphSeePresentEntitiesAndParticles) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;Ljava/util/function/Predicate;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V", remap = true),
            remap = false)
    private void roundabout$renderParticleAtStoppedTime(Particle particle, VertexConsumer vertices, Camera camera, float partialTick) {
        if (this.level != null && !((TimeStop) this.level).getTimeStoppingEntities().isEmpty()) {
            IParticleAccess timeState = (IParticleAccess) particle;
            if (!timeState.roundabout$getRoundaboutIsTimeStopCreated() && !(particle instanceof ItemPickupParticle)) {
                AccessParticle position = (AccessParticle) particle;
                Vec3i range = new Vec3i((int) position.roundabout$getX(),
                        (int) position.roundabout$getY(), (int) position.roundabout$getZ());
                if (((TimeStop) this.level).inTimeStopRange(range)) {
                    partialTick = timeState.roundabout$getPreTSTick();
                } else {
                    timeState.roundabout$setPreTSTick();
                }
            }
        }
        particle.render(vertices, camera, partialTick);
    }

    @Shadow
    protected ClientLevel level;
}
