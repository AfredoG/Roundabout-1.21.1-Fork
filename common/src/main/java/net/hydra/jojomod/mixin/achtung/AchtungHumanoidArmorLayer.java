package net.hydra.jojomod.mixin.achtung;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.access.IEntityAndData;
import net.hydra.jojomod.client.ClientNetworking;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.event.powers.StandUserClient;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value =HumanoidArmorLayer.class, priority = 100)
public abstract class AchtungHumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    /***
     * Code for Achtung Baby Armor Rendering!
     * When you switch to third person, the armor is fully solid without this mixin.
     * This mixin adds support for armor rendering transparency. :D
     *
     * HOWEVER, Forge replaced Minecraft armor rendering entirely (cringe), so
     * there is a separate forge only mixin for this as well.
     *
     */

    @Inject(method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;ILnet/minecraft/resources/ResourceLocation;)V",
            at = @At("HEAD"), cancellable = true)
    private void roundabout$fadeArmor(PoseStack pose, MultiBufferSource buffers, int light,
                                      net.minecraft.client.model.Model model, int color,
                                      ResourceLocation texture, CallbackInfo ci) {
        float fade = ClientUtil.getThrowFadeToTheEther();
        if (fade == 1.0F) return;
        if (fade > 0.0F) {
            int alpha = net.minecraft.util.Mth.clamp(Math.round((color >>> 24) * fade), 0, 255);
            VertexConsumer vertices = buffers.getBuffer(RenderType.entityTranslucentCull(texture));
            model.renderToBuffer(pose, vertices, light, OverlayTexture.NO_OVERLAY,
                    (alpha << 24) | (color & 0x00FFFFFF));
        }
        ci.cancel();
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "HEAD"),cancellable = true)
    public void roundabout$Render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, CallbackInfo ci) {
        IEntityAndData entityAndData = ((IEntityAndData) $$3);
        if (entityAndData.roundabout$getTrueInvisibility() > -1
                && (!ClientUtil.checkIfClientCanSeeInvisAchtung())
                && ClientNetworking.getAppropriateConfig() != null &&
                ClientNetworking.getAppropriateConfig().achtungSettings != null &&
                ClientNetworking.getAppropriateConfig().achtungSettings.hidesArmor && !ClientUtil.checkIfClientCanSeeMobsForWindVision() || ClientUtil.checkIfClientCanSeeMobsForWindVision() && entityAndData.roundabout$getTrueInvisibilityManhattan() < 1) {
            ci.cancel();
            return;
        }
    }

    /**Shadows, ignore
     * -------------------------------------------------------------------------------------------------------------
     * */

    public AchtungHumanoidArmorLayer(RenderLayerParent<T, M> $$0) {
        super($$0);
    }

}