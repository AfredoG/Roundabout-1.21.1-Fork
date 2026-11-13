package net.hydra.jojomod.client.models.worn_stand;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.client.models.PsuedoHierarchicalModel;
import net.hydra.jojomod.client.models.layers.animations.CatchTheRainbowAnimations;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.event.powers.TimeStop;
import net.hydra.jojomod.stand.powers.Powers20thCenturyBoy;
import net.hydra.jojomod.stand.powers.PowersCatchTheRainbow;
import net.hydra.jojomod.util.MainUtil;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CatchTheRainbowModel extends PsuedoHierarchicalModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "catchtherainbow"), "main");
	private final ModelPart stand;
	private final ModelPart Root;

	public CatchTheRainbowModel() {
		this.Root = createBodyLayer().bakeRoot();
		this.stand = Root.getChild("stand");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stand = partdefinition.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.45F)), PartPose.offset(0.5F, 20.5F, 5.75F));

		PartDefinition forehead_r1 = stand.addOrReplaceChild("forehead_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -8.0F, -4F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity var1, float ageInTicks) {

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
	public ModelPart root() {return Root;}

	public static ResourceLocation base = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/base.png");
	public static ResourceLocation ghast_dry = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/ghast_dry.png");
	public static ResourceLocation ghast_happy = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/ghast_happy.png");
	public static ResourceLocation lali_ho = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/lali_ho.png");
	public static ResourceLocation eye_white = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/eye_white.png");
	public static ResourceLocation eye_red = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/eye_red.png");
	public static ResourceLocation skeletonctr = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/skeleton.png");
	public static ResourceLocation neon_on = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/neon_on.png");
	public static ResourceLocation neon_off = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/neon_off.png");
	public static ResourceLocation strider_healthy = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/strider_healthy.png");
	public static ResourceLocation strider_sick = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/strider_sick.png");
	public static ResourceLocation cmy = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/cmy.png");
	public static ResourceLocation magma = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,
			"textures/stand/catch_the_rainbow/magma.png");

	public ResourceLocation getTextureLocation(Entity context, byte skin) {
		if ( ((StandUser) context).roundabout$getStandPowers() instanceof PowersCatchTheRainbow PCR && !PCR.isInRain()) {
			switch (skin) {
				case PowersCatchTheRainbow.GHAST -> {
					return ghast_dry;
				}
				case PowersCatchTheRainbow.LALI_HO -> {
					return lali_ho;
				}
				case PowersCatchTheRainbow.EYE -> {
					return eye_white;
				}
				case PowersCatchTheRainbow.SKELETONCTR -> {
					return skeletonctr;
				}
				case PowersCatchTheRainbow.NEON -> {
					return neon_off;
				}
				case PowersCatchTheRainbow.STRIDER -> {
					return strider_sick;
				}
				case PowersCatchTheRainbow.CMY -> {
					return cmy;
				}
				case PowersCatchTheRainbow.MAGMA -> {
					return magma;
				}
				default -> {
					return base;
				}
			}
		}
		else {
			switch (skin) {
				case PowersCatchTheRainbow.GHAST -> {
					return ghast_happy;
				}
				case PowersCatchTheRainbow.LALI_HO -> {
					return lali_ho;
				}
				case PowersCatchTheRainbow.EYE -> {
					return eye_red;
				}
				case PowersCatchTheRainbow.SKELETONCTR -> {
					return skeletonctr;
				}
				case PowersCatchTheRainbow.NEON -> {
					return neon_on;
				}
				case PowersCatchTheRainbow.STRIDER -> {
					return strider_healthy;
				}
				case PowersCatchTheRainbow.CMY -> {
					return cmy;
				}
				case PowersCatchTheRainbow.MAGMA -> {
					return magma;
				}
				default -> {
					return base;
				}
			}
		}
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
			user.roundabout$getWornStandIdleAnimation().startIfStopped(context.tickCount);


			byte idle = MainUtil.getWornStandIdleSafe(user, PowersCatchTheRainbow.class);
			/*byte idle = (byte)0;
			if (user.roundabout$getStandPowers() instanceof PowersCatchTheRainbow) {
				idle = user.roundabout$getIdlePos();
			}else if (user.roundabout$getPreviousStandPowers() instanceof PowersCatchTheRainbow) {
				idle = user.roundabout$getPreviousPose();
			}*/


			if (idle != 0) {
				user.roundabout$getWornStandIdleAnimation().startIfStopped(context.tickCount);
			}
			else {
				user.roundabout$getWornStandIdleAnimation().stop();
			}

			if (idle == 1) {
				this.animate(user.roundabout$getWornStandIdleAnimation(), CatchTheRainbowAnimations.openright, partialTicks, 1f);
			}
			if (idle == 2) {
				this.animate(user.roundabout$getWornStandIdleAnimation(), CatchTheRainbowAnimations.openleft, partialTicks, 1f);
			}

			root().render(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, r, g, b));
		}
	}
}