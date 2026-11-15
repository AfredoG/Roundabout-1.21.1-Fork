package net.hydra.jojomod.mixin.gravity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.hydra.jojomod.util.gravity.GravityAPI;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityRenderer.class, priority = 1001)
public abstract class GravityEntityRendererMixin<T extends Entity> {
    // Keep vanilla attachments, partial-tick positioning and NeoForge nameplate rules.
    @Redirect(
            method = "renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;cameraOrientation()Lorg/joml/Quaternionf;")
    )
    private Quaternionf roundabout$rotateNameTag(EntityRenderDispatcher dispatcher, T entity, Component name,
                                                PoseStack poseStack, MultiBufferSource buffers, int packedLight, float partialTick) {
        Quaternionf cameraRotation = dispatcher.cameraOrientation();
        Direction gravityDirection = GravityAPI.getGravityDirection(entity);
        if (gravityDirection == Direction.DOWN) {
            return cameraRotation;
        }
        return new Quaternionf(RotationUtil.getCameraRotationQuaternion(gravityDirection))
                .conjugate().mul(cameraRotation);
    }
}
