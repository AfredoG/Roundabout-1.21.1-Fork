package net.hydra.jojomod.client.models.stand.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.client.models.layers.ModEntityRendererClient;
import net.hydra.jojomod.client.models.stand.StandModel;
import net.hydra.jojomod.client.models.stand.StarPlatinumModel;
import net.hydra.jojomod.client.models.stand.SurvivorModel;
import net.hydra.jojomod.entity.stand.StandEntity;
import net.hydra.jojomod.entity.stand.SurvivorEntity;
import net.hydra.jojomod.entity.stand.TheWorldEntity;
import net.hydra.jojomod.stand.powers.PowersSurvivor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class SurvivorRenderer<M extends StandEntity> extends StandRenderer<SurvivorEntity> {

    private static final ResourceLocation BASE = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/base.png");
    private static final ResourceLocation BASE_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/base.png");

    private static final ResourceLocation RED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/red.png");
    private static final ResourceLocation RED_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/red.png");

    private static final ResourceLocation BLUE = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/blue.png");
    private static final ResourceLocation BLUE_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/blue.png");

    private static final ResourceLocation PURPLE = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/purple.png");
    private static final ResourceLocation PURPLE_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/purple.png");

    private static final ResourceLocation SILVER = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/silver.png");
    private static final ResourceLocation SILVER_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/silver.png");

    private static final ResourceLocation GREEN = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/green.png");
    private static final ResourceLocation GREEN_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/green.png");

    private static final ResourceLocation GHAST = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/ghast.png");
    private static final ResourceLocation GHAST_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/ghast.png");

    private static final ResourceLocation ENDER = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/ender.png");
    private static final ResourceLocation ENDER_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/ender.png");

    private static final ResourceLocation CONDUIT = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/conduit.png");
    private static final ResourceLocation CONDUIT_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/conduit.png");

    private static final ResourceLocation CAKE = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/cake.png");
    private static final ResourceLocation CAKE_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/cake.png");

    private static final ResourceLocation SCULK = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/sculk.png");
    private static final ResourceLocation SCULK_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/sculk.png");

    private static final ResourceLocation CENTURIAN = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/centurian.png");
    private static final ResourceLocation CENTURIAN_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/centurian.png");

    private static final ResourceLocation BLOB = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/blob.png");
    private static final ResourceLocation BLOB_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/blob.png");

    private static final ResourceLocation SPONGE = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/sponge.png");
    private static final ResourceLocation SPONGE_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/sponge.png");

    private static final ResourceLocation REDSTONE = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/redstone.png");
    private static final ResourceLocation REDSTONE_ACTIVATED = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/survivor/activated/redstone.png");

    public SurvivorRenderer(EntityRendererProvider.Context context) {
        super(context, new SurvivorModel<>(context.bakeLayer(ModEntityRendererClient.SURVIVOR_LAYER)),0f);
    }

    @Override
    public ResourceLocation getTextureLocation(SurvivorEntity entity) {
        byte BT = entity.getSkin();
        if (entity.getActivated()){
            if (BT == PowersSurvivor.RED)
                return RED_ACTIVATED;
            if (BT == PowersSurvivor.GREEN)
                return GREEN_ACTIVATED;
            if (BT == PowersSurvivor.PURPLE)
                return PURPLE_ACTIVATED;
            if (BT == PowersSurvivor.BLUE)
                return BLUE_ACTIVATED;
            if (BT == PowersSurvivor.SILVER)
                return SILVER_ACTIVATED;
            if (BT == PowersSurvivor.GHAST)
                return GHAST_ACTIVATED;
            if (BT == PowersSurvivor.ENDER)
                return ENDER_ACTIVATED;
            if (BT == PowersSurvivor.CONDUIT)
                return CONDUIT_ACTIVATED;
            if (BT == PowersSurvivor.CAKE)
                return CAKE_ACTIVATED;
            if (BT == PowersSurvivor.SPONGE)
                return SPONGE_ACTIVATED;
            if (BT == PowersSurvivor.SCULK)
                return SCULK_ACTIVATED;
            if (BT == PowersSurvivor.CENTURIAN)
                return CENTURIAN_ACTIVATED;
            if (BT == PowersSurvivor.BLOB)
                return BLOB_ACTIVATED;
            if (BT == PowersSurvivor.REDSTONE)
                return REDSTONE_ACTIVATED;
            return BASE_ACTIVATED;
        }
        if (BT == PowersSurvivor.RED)
            return RED;
        if (BT == PowersSurvivor.GREEN)
            return GREEN;
        if (BT == PowersSurvivor.PURPLE)
            return PURPLE;
        if (BT == PowersSurvivor.BLUE)
            return BLUE;
        if (BT == PowersSurvivor.SILVER)
            return SILVER;
        if (BT == PowersSurvivor.GHAST)
            return GHAST;
        if (BT == PowersSurvivor.ENDER)
            return ENDER;
        if (BT == PowersSurvivor.CONDUIT)
            return CONDUIT;
        if (BT == PowersSurvivor.CAKE)
            return CAKE;
        if (BT == PowersSurvivor.SPONGE)
            return SPONGE;
        if (BT == PowersSurvivor.SCULK)
            return SCULK;
        if (BT == PowersSurvivor.CENTURIAN)
            return CENTURIAN;
        if (BT == PowersSurvivor.BLOB)
            return BLOB;
        if (BT == PowersSurvivor.REDSTONE)
            return REDSTONE;
        return BASE;
    }

    @Override
    public void render(SurvivorEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        float factor = 0.8F + mobEntity.getRandomSize();
        if (mobEntity.isBaby()) {
            matrixStack.scale(0.5f*factor, 0.5f*factor, 0.5f*factor);
        } else {
            matrixStack.scale(0.87f * factor, 0.87f * factor, 0.87f * factor);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(SurvivorEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return super.getRenderType(entity, showBody, true, showOutline);
    }


}

