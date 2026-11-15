package net.hydra.jojomod.mixin.gravity.client;

import net.hydra.jojomod.access.IGravityEntity;
import net.hydra.jojomod.client.ClientUtil;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GravityGameRendererMixin {
    @Inject(method = "renderLevel(Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"))
    private void roundabout$updateGravityBeforeCamera(CallbackInfo ci) {
        if (ClientUtil.getPlayer() != null) {
            IGravityEntity gravity = (IGravityEntity) ClientUtil.getPlayer();
            if (gravity.roundabout$canChangeGravity()) {
                gravity.roundabout$updateGravityStatus();
                gravity.roundabout$applyGravityChange();
            }
        }
    }
}
