package net.hydra.jojomod.client.models.worn_stand;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.client.models.PsuedoHierarchicalModel;
import net.hydra.jojomod.client.models.stand.ChairRattModel;
import net.hydra.jojomod.client.models.stand.ReddModel;
import net.hydra.jojomod.entity.stand.RattEntity;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.event.powers.TimeStop;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ChairRattShoulderModel extends PsuedoHierarchicalModel {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
   private final ModelPart stand;
    private final ModelPart Root;

    public ChairRattShoulderModel() {
        super(RenderType::entityTranslucent);

        this.Root = createBodyLayer().bakeRoot();
        this.stand = Root.getChild("stand");
    }

    public static LayerDefinition createBodyLayer() {
        return ChairRattModel.getTexturedModelData();
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int argbColor) {
        float red = net.minecraft.util.FastColor.ARGB32.red(argbColor) / 255.0F;
        float green = net.minecraft.util.FastColor.ARGB32.green(argbColor) / 255.0F;
        float blue = net.minecraft.util.FastColor.ARGB32.blue(argbColor) / 255.0F;
        float alpha = net.minecraft.util.FastColor.ARGB32.alpha(argbColor) / 255.0F;

        stand.render(poseStack, vertexConsumer, packedLight, packedOverlay, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, red, green, blue));
    }

    @Override
    public ModelPart root() {
        return Root;
    }

    @Override
    public void setupAnim(Entity var1, float pAgeInTicks) {
    }

    public static ResourceLocation chair = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
            "textures/stand/ratt/chair.png");
    public static ResourceLocation king_chair = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
            "textures/stand/ratt/king_chair.png");
    public static ResourceLocation mech_chair = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
            "textures/stand/ratt/mech_chair.png");


    public ResourceLocation getTextureLocation(Entity context, byte skin){
        return switch (skin) {
            case RattEntity.KING_RAT_SKIN -> king_chair;
            case RattEntity.MECH_RAT_SKIN -> mech_chair;
            default -> chair;
        };
    }


    public void render(Entity context, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(context, (byte)0)));
        root().render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY);
    }
    public void render(Entity context, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource,
                       int light, float r, float g, float b, float alpha, byte skin) {
        if (context instanceof LivingEntity LE) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
           if (((TimeStop)context.level()).CanTimeStopEntity(context) || ClientUtil.checkIfGamePaused()){
                partialTicks = 0;
            }
            StandUser user = ((StandUser) LE);
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation(context, skin)));

            root().render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, r, g, b));
        }
    }

}

