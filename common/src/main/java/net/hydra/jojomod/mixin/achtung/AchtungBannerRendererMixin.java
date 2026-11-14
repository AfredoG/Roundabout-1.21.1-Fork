package net.hydra.jojomod.mixin.achtung;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.hydra.jojomod.client.ClientUtil;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BannerRenderer.class)
public class AchtungBannerRendererMixin {

    /**Banners arent see thru without extra work*/
    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"), cancellable = true)
    public void roundabout$renderInvis(BannerBlockEntity $$0, float $$1, PoseStack $$2,
                                       MultiBufferSource $$3, int $$4, int $$5, CallbackInfo ci) {
        if (ClientUtil.getThrowFadeToTheEther() != 1){
            ci.cancel();
            BannerPatternLayers $$6 = $$0.getPatterns();
            float $$7 = 0.6666667F;
            boolean $$8 = $$0.getLevel() == null;
            $$2.pushPose();
            long $$9;
            if ($$8) {
                $$9 = 0L;
                $$2.translate(0.5F, 0.5F, 0.5F);
                this.pole.visible = true;
            } else {
                $$9 = $$0.getLevel().getGameTime();
                BlockState $$11 = $$0.getBlockState();
                if ($$11.getBlock() instanceof BannerBlock) {
                    $$2.translate(0.5F, 0.5F, 0.5F);
                    float $$12 = -RotationSegment.convertToDegrees($$11.getValue(BannerBlock.ROTATION));
                    $$2.mulPose(Axis.YP.rotationDegrees($$12));
                    this.pole.visible = true;
                } else {
                    $$2.translate(0.5F, -0.16666667F, 0.5F);
                    float $$13 = -$$11.getValue(WallBannerBlock.FACING).toYRot();
                    $$2.mulPose(Axis.YP.rotationDegrees($$13));
                    $$2.translate(0.0F, -0.3125F, -0.4375F);
                    this.pole.visible = false;
                }
            }

            $$2.pushPose();
            $$2.scale(0.6666667F, -0.6666667F, -0.6666667F);
            VertexConsumer $$14 = ModelBakery.BANNER_BASE.buffer($$3, RenderType::entityTranslucent);
            this.pole.render($$2, $$14, $$4, $$5);
            this.bar.render($$2, $$14, $$4, $$5);
            BlockPos $$15 = $$0.getBlockPos();
            float $$16 = ((float)Math.floorMod((long)($$15.getX() * 7 + $$15.getY() * 9 + $$15.getZ() * 13) + $$9, 100L) + $$1) / 100.0F;
            this.flag.xRot = (-0.0125F + 0.01F * Mth.cos((float) (Math.PI * 2) * $$16)) * (float) Math.PI;
            this.flag.y = -32.0F;
            renderPatterns($$2, $$3, $$4, $$5, this.flag, ModelBakery.BANNER_BASE, true, $$0.getBaseColor(), $$6);
            $$2.popPose();
            $$2.popPose();
        }
    }

    @Inject(method = "renderPatterns(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/resources/model/Material;ZLnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/level/block/entity/BannerPatternLayers;Z)V", at = @At("HEAD"), cancellable = true)
    private static void roundabout$renderPatterns(PoseStack $$0,
                                                  MultiBufferSource $$1,
                                                  int $$2,
                                                  int $$3,
                                                  ModelPart $$4,
                                                  Material $$5,
                                                  boolean $$6,
                                                  DyeColor baseColor,
                                                  BannerPatternLayers $$7,
                                                  boolean $$8, CallbackInfo ci) {
        if (ClientUtil.getThrowFadeToTheEther() != 1) {
            ci.cancel();
            $$4.render($$0, $$5.buffer($$1, RenderType::entityTranslucent, $$8), $$2, $$3);

            Material baseMaterial = $$6 ? Sheets.BANNER_BASE : Sheets.SHIELD_BASE;
            $$4.render($$0, baseMaterial.buffer($$1, RenderType::entityTranslucent), $$2, $$3, baseColor.getTextureDiffuseColor());
            for (int i = 0; i < 16 && i < $$7.layers().size(); i++) {
                BannerPatternLayers.Layer layer = $$7.layers().get(i);
                Material material = $$6 ? Sheets.getBannerMaterial(layer.pattern()) : Sheets.getShieldMaterial(layer.pattern());
                $$4.render($$0, material.buffer($$1, RenderType::entityTranslucent), $$2, $$3, layer.color().getTextureDiffuseColor());
            }
        }
    }
    /**Shadows, ignore
     * -------------------------------------------------------------------------------------------------------------
     * */


    @Shadow
    @Final
    private ModelPart flag;
    @Shadow
    @Final
    private ModelPart pole;
    @Shadow
    @Final
    private ModelPart bar;
    @Shadow
    public static void renderPatterns(
            PoseStack $$0, MultiBufferSource $$1, int $$2, int $$3, ModelPart $$4, Material $$5, boolean $$6, DyeColor baseColor, BannerPatternLayers $$7
    ) {
    }
}
