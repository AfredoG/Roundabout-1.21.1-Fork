package net.hydra.jojomod.client.models.projectile;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.Roundabout;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class KnifeModel extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID, "knife"), "main");
	private final ModelPart knife;

	public KnifeModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.knife = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition knife = partdefinition.addOrReplaceChild("knife", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 2.0F, -0.5F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition handle = knife.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -1.95F, 10.05F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.05F)), PartPose.offset(0.5F, -0.5F, -10.0F));

		PartDefinition blade = knife.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 3.75F, -2.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(5, 2).addBox(0.0F, 3.75F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.25F, -3.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int argbColor) {
        float red = net.minecraft.util.FastColor.ARGB32.red(argbColor) / 255.0F;
        float green = net.minecraft.util.FastColor.ARGB32.green(argbColor) / 255.0F;
        float blue = net.minecraft.util.FastColor.ARGB32.blue(argbColor) / 255.0F;
        float alpha = net.minecraft.util.FastColor.ARGB32.alpha(argbColor) / 255.0F;

		knife.render(poseStack, vertexConsumer, packedLight, packedOverlay, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, red, green, blue));
	}

}