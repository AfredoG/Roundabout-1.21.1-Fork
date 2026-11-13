package net.hydra.jojomod.client.models.substand.renderers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.access.IPlayerEntity;
import net.hydra.jojomod.block.SafePlaceContext;
import net.hydra.jojomod.client.models.layers.ModEntityRendererClient;
import net.hydra.jojomod.client.models.stand.renderers.StandRenderer;
import net.hydra.jojomod.client.models.substand.LifeTrackerModel;
import net.hydra.jojomod.client.models.substand.SeperatedLegsModel;
import net.hydra.jojomod.entity.stand.CinderellaEntity;
import net.hydra.jojomod.entity.stand.GreenDayEntity;
import net.hydra.jojomod.entity.substand.LifeTrackerEntity;
import net.hydra.jojomod.entity.substand.SeperatedLegsEntity;
import net.hydra.jojomod.event.powers.visagedata.VisageData;
import net.hydra.jojomod.item.MaskItem;
import net.hydra.jojomod.item.ModificationMaskItem;
import net.hydra.jojomod.util.MainUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Map;
import java.util.Objects;

public class SeperatedLegsRenderer extends StandRenderer<SeperatedLegsEntity> {
    private static final ResourceLocation PART_FIVE_GREEN_DAY = ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID,"textures/stand/green_day/part_four_green_day.png");

    public SeperatedLegsRenderer(EntityRendererProvider.Context context) {
        super(context,new SeperatedLegsModel<>(context.bakeLayer(ModEntityRendererClient.SEPERATED_LEGS_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(SeperatedLegsEntity seperatedLegsEntity) {
        //Player user = (Player)seperatedLegsEntity.getUser();
        //GameProfile profile = user.getGameProfile();
        Player P =((Player)seperatedLegsEntity.getUser());
        if( P != null) {
            if (P.getGameProfile() != null) {
                return MainUtil.getPlayerSkinWithRespectToVisage((Player) seperatedLegsEntity.getUser());
            } else {
                return PART_FIVE_GREEN_DAY;
            }
        }else{
            return PART_FIVE_GREEN_DAY;
        }
    }

    private ResourceLocation getSkin(GameProfile gameProfile) {
        if (gameProfile == null || gameProfile.getId() == null || gameProfile.getName() == null || gameProfile.getName().isEmpty()) {
            return PART_FIVE_GREEN_DAY;
        }
        return net.hydra.jojomod.util.SkinUtil.getSkin(gameProfile).texture();
    }

    @Override
    public void render(SeperatedLegsEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        if (mobEntity.getUser() instanceof Player P) {
            IPlayerEntity pl = ((IPlayerEntity) P);
            ItemStack visage = pl.roundabout$getMaskSlot();
            if (Objects.nonNull(visage)) {
                if (visage.getItem() instanceof MaskItem MI) {
                    if (MI instanceof ModificationMaskItem MD){
                        int height = net.hydra.jojomod.util.ItemData.elementOrEmpty(visage, "modifications").getInt("height");
                        int width = net.hydra.jojomod.util.ItemData.elementOrEmpty(visage, "modifications").getInt("width");
                        matrixStack.scale(0.798F + (((float) height)*0.001F), 0.7F+(((float) width)*0.001F), 0.798F+(((float) height)*0.001F));
                    } else {
                        VisageData vd = MI.visageData.generateVisageData(P);
                        Vector3f scale = vd.scale();
                        matrixStack.scale(scale.y, scale.z, scale.x);
                    }
                }
            }
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}
