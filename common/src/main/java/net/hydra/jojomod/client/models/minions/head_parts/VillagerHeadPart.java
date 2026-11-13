package net.hydra.jojomod.client.models.minions.head_parts;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.client.models.PsuedoHierarchicalModel;
import net.hydra.jojomod.entity.zombie_minion.AxolotlMinion;
import net.hydra.jojomod.entity.zombie_minion.DogMinion;
import net.hydra.jojomod.entity.zombie_minion.OcelotMinion;
import net.hydra.jojomod.entity.zombie_minion.ParrotMinion;
import net.hydra.jojomod.event.powers.TimeStop;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class VillagerHeadPart extends PsuedoHierarchicalModel {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
   private final ModelPart head;
    private final ModelPart Root;

    public VillagerHeadPart() {
        super(RenderType::entityTranslucent);

        this.Root = createBodyLayer().bakeRoot();
        this.head = Root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        $$2.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
        $$2.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        return LayerDefinition.create($$0, 64, 64);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int argbColor) {
        float red = net.minecraft.util.FastColor.ARGB32.red(argbColor) / 255.0F;
        float green = net.minecraft.util.FastColor.ARGB32.green(argbColor) / 255.0F;
        float blue = net.minecraft.util.FastColor.ARGB32.blue(argbColor) / 255.0F;
        float alpha = net.minecraft.util.FastColor.ARGB32.alpha(argbColor) / 255.0F;

        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, red, green, blue));
    }

    @Override
    public ModelPart root() {
        return Root;
    }

    @Override
    public void setupAnim(Entity var1, float pAgeInTicks) {

    }

    /**Idle 1 (byte 0) = head straight, idle 2 (byte 1) = head follow*/


    public ResourceLocation getTextureLocation(){
        return ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID, "textures/entity/minions/villager.png");
    }

    public void render(Entity context, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation()));
        root().render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY);
    }
    public void render(Entity context, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource,
                       int light, float r, float g, float b, float alpha) {
        if (context instanceof LivingEntity LE) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            if (context instanceof ParrotMinion pm){
                head.xScale = 0.7F;
                head.yScale = 0.7F;
                head.zScale = 0.7F;
                head.y += 1F;
            }
            if (context instanceof DogMinion pm){
                head.xScale = 0.9F;
                head.yScale = 0.9F;
                head.zScale = 0.9F;
                head.x += 1F;
                head.y += 3F;
            }
            if (context instanceof OcelotMinion pm){
                head.xScale = 0.9F;
                head.yScale = 0.9F;
                head.zScale = 0.9F;
                head.y += 2F;
            }
            if (context instanceof AxolotlMinion pm){
                head.xScale = 0.98F;
                head.yScale = 0.98F;
                head.zScale = 0.98F;
            }
            if (((TimeStop)context.level()).CanTimeStopEntity(context) || ClientUtil.checkIfGamePaused()){
                partialTicks = 0;
            }
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation()));
            //The number at the end is inversely proportional so 2 is half speed
            root().render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, r, g, b));
        }
    }

}

