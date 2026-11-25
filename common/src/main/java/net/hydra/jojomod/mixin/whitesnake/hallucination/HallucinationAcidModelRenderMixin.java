package net.hydra.jojomod.mixin.whitesnake.hallucination;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.block.ModBlocks;
import net.hydra.jojomod.event.ModEffects;
import net.hydra.jojomod.event.powers.whitesnake.HallucinationEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBlockRenderer.class)
public abstract class HallucinationAcidModelRenderMixin {
    @Inject(method = "tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;JILnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V", at = @At("HEAD"), cancellable = true)
    private void roundaboutWhitesnake$hideAcidModel(BlockAndTintGetter level, BakedModel model,
                                                     BlockState state, BlockPos pos, PoseStack pose,
                                                     VertexConsumer consumer, boolean checkSides,
                                                     RandomSource random, long seed, int overlay, ModelData modelData, RenderType renderType,
                                                     CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || !(state.is(ModBlocks.HALLUCINATORY_ACID)
                || state.is(ModBlocks.HALLUCINATORY_ACID_WALL))) return;
        MobEffectInstance effect = minecraft.player.getEffect(ModEffects.HALLUCINATION);
        if (HallucinationEffect.hasDistortion(effect)) ci.cancel();
    }
}
