package net.hydra.jojomod.client.models;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.event.powers.StandUser;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class WornStoneMaskModel<T extends Entity> extends PsuedoHierarchicalModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "speedwagon_hat_converted"), "main");
	private final ModelPart head;
    private final ModelPart root;

	public WornStoneMaskModel() {
        super(RenderType::entityTranslucent);

        this.root = createBodyLayer().bakeRoot();
        this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(3, 3).addBox(-4.0F, -6.5F, -4.75F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(3, 19).addBox(-4.0F, -6.5F, -4.75F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	/* @Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	} */

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
        return root;
    }

    @Override
    public void setupAnim(Entity var1, float pAgeInTicks) {
    }
    public ResourceLocation getTextureLocation(){
        return ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
                "textures/entity/other_layers/stone_mask_worn.png");
    }

    public void render(Entity context, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, float r, float g, float b, float heyFull) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation()));
        root().render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY);
    }
    public void render(Entity context, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource,
                       int light, float r, float g, float b, float alpha, byte skin) {
        if (context instanceof LivingEntity LE) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation()));
            //r = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation(context, skin)));
            root().render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, r, g, b));
        }
    }
}