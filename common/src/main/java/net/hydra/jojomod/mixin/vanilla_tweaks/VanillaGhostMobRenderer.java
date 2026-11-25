package net.hydra.jojomod.mixin.vanilla_tweaks;

import com.mojang.blaze3d.vertex.PoseStack;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.util.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class VanillaGhostMobRenderer {

    /**Vanilla ghost mob config makes them invisible*/

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "HEAD"),cancellable = true)
    private void roundabout$render(LivingEntity $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5, CallbackInfo ci) {
        if ($$0 instanceof Ghast || $$0 instanceof Allay || $$0 instanceof Vex) {
            if (ConfigManager.getClientConfig().vanillaMinecraftTweaks.onlyStandUsersCanSeeVanillaGhostMobs) {
                LocalPlayer lp = Minecraft.getInstance().player;
                if (lp !=null) {
                    if (!ClientUtil.canSeeStands(lp) &&
                            !lp.isSpectator()) {
                        ci.cancel();
                    }
                }
            }
        }

    }

    /**Shadows, ignore
     * -------------------------------------------------------------------------------------------------------------
     * */

}
