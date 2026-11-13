package net.hydra.jojomod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.stand.powers.PowersDiverDown;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public final class DiverDownPointerRenderer {
    private static final ResourceLocation POINTER_ICON = ResourceLocation.fromNamespaceAndPath(
            Roundabout.MOD_ID, "textures/particle/pointer_diver_down.png");

    private DiverDownPointerRenderer() {}

    public static void render(LivingEntity entity, PoseStack poseStack, MultiBufferSource bufferSource) {
        LocalPlayer viewer = Minecraft.getInstance().player;
        if (viewer == null || entity == viewer) {
            return;
        }

        if (((StandUser) viewer).roundabout$getStandPowers() instanceof PowersDiverDown dd) {
            if (dd.isDiveActive() && !dd.isSelfDive() && dd.submergedTarget == entity) {
                float size = 0.30F;

                poseStack.pushPose();
                poseStack.translate(0, entity.getBbHeight() + 0.86F, 0);
                poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

                VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.text(POINTER_ICON));
                Matrix4f matrix = poseStack.last().pose();
                com.mojang.blaze3d.vertex.PoseStack.Pose normal = poseStack.last();

                vertexConsumer.addVertex(matrix, -size, -size, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(normal, 0, 0, -1);
                vertexConsumer.addVertex(matrix, size, -size, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(normal, 0, 0, -1);
                vertexConsumer.addVertex(matrix, size, size, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(normal, 0, 0, -1);
                vertexConsumer.addVertex(matrix, -size, size, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(normal, 0, 0, -1);

                RenderSystem.enableDepthTest();
                poseStack.popPose();
            }
        }
    }
}