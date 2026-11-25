package net.hydra.jojomod.mixin.whitesnake.hallucination;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.block.ModBlocks;
import net.hydra.jojomod.event.ModEffects;
import net.hydra.jojomod.event.powers.whitesnake.HallucinationEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderDispatcher.class)
public abstract class HallucinationAcidRenderMixin {
    @Inject(method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;Lnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V", at = @At("HEAD"), cancellable = true)
    private void roundaboutWhitesnake$hideAcidInWorld(BlockState state, BlockPos pos,
                                                      BlockAndTintGetter level, PoseStack pose,
                                                      VertexConsumer consumer, boolean checkSides,
                                                      RandomSource random, ModelData modelData, RenderType renderType, CallbackInfo ci) {
        if (roundaboutWhitesnake$acidIsHidden(state)) ci.cancel();
    }

    @Inject(method = "renderSingleBlock(Lnet/minecraft/world/level/block/state/BlockState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V", at = @At("HEAD"), cancellable = true)
    private void roundaboutWhitesnake$hideSingleAcidBlock(BlockState state, PoseStack pose,
                                                          MultiBufferSource buffers, int light,
                                                          int overlay, ModelData modelData, RenderType renderType, CallbackInfo ci) {
        if (roundaboutWhitesnake$acidIsHidden(state)) ci.cancel();
    }

    private static boolean roundaboutWhitesnake$acidIsHidden(BlockState state) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || !(state.is(ModBlocks.HALLUCINATORY_ACID)
                || state.is(ModBlocks.HALLUCINATORY_ACID_WALL))) return false;
        MobEffectInstance effect = minecraft.player.getEffect(ModEffects.HALLUCINATION);
        return HallucinationEffect.hasDistortion(effect);
    }
}
