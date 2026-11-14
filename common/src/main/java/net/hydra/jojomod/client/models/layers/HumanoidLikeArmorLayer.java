package net.hydra.jojomod.client.models.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.access.IPlayerEntity;
import net.hydra.jojomod.client.ClientPlatform;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.entity.visages.JojoNPC;
import net.hydra.jojomod.client.models.visages.PlayerLikeModel;
import net.hydra.jojomod.event.index.ShapeShifts;
import net.hydra.jojomod.event.powers.StandUserClient;
import net.hydra.jojomod.event.powers.TimeStop;
import net.hydra.jojomod.item.MaskItem;
import net.hydra.jojomod.platform.services.IPlatformHelperClient;
import net.hydra.jojomod.util.config.ConfigManager;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.spongepowered.asm.mixin.Unique;

public class HumanoidLikeArmorLayer<T extends JojoNPC, M extends PlayerLikeModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final A innerModel;
    private final A outerModel;
    private final TextureAtlas armorTrimAtlas;

    @Unique
    public int roundabout$ArmorPhase;
    @Unique
    public boolean roundabout$ModifyEntity;
    @Unique
    public @org.jetbrains.annotations.Nullable ItemStack roundabout$RenderChest;
    @Unique
    public @org.jetbrains.annotations.Nullable ItemStack roundabout$RenderLegs;
    @Unique
    public @org.jetbrains.annotations.Nullable ItemStack roundabout$RenderBoots;
    @Unique
    public @org.jetbrains.annotations.Nullable ItemStack roundabout$RenderHead;

    public HumanoidLikeArmorLayer(RenderLayerParent<T, M> $$0, A $$1, A $$2, ModelManager $$3) {
        super($$0);
            this.innerModel = $$1;
            this.outerModel = $$2;
            this.armorTrimAtlas = $$3.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
        roundabout$ArmorPhase = 0;
        if ($$3.host != null) {
            IPlayerEntity ipe = ((IPlayerEntity) $$3.host);
            if (ShapeShifts.getShiftFromByte(ipe.roundabout$getShapeShift()) == ShapeShifts.EERIE){
                return;
            }
            StandUserClient userclient = ((StandUserClient) $$3);

            roundabout$ModifyEntity = ((TimeStop) $$3.level()).CanTimeStopEntity($$3) || ClientUtil.getScreenFreeze();
            if (roundabout$ModifyEntity) {
                if (userclient.roundabout$getRoundaboutRenderChest() == null){
                    userclient.roundabout$setRoundaboutRenderChest($$3.getItemBySlot(EquipmentSlot.CHEST).copy());
                }
                if (userclient.roundabout$getRoundaboutRenderLegs() == null){
                    userclient.roundabout$setRoundaboutRenderLegs($$3.getItemBySlot(EquipmentSlot.LEGS).copy());
                }
                if (userclient.roundabout$getRoundaboutRenderBoots() == null){
                    userclient.roundabout$setRoundaboutRenderBoots($$3.getItemBySlot(EquipmentSlot.FEET).copy());
                }
                if (userclient.roundabout$getRoundaboutRenderHead() == null){
                    userclient.roundabout$setRoundaboutRenderHead($$3.getItemBySlot(EquipmentSlot.HEAD).copy());
                }
                roundabout$RenderChest = userclient.roundabout$getRoundaboutRenderChest();
                roundabout$RenderLegs = userclient.roundabout$getRoundaboutRenderLegs();
                roundabout$RenderBoots = userclient.roundabout$getRoundaboutRenderBoots();
                roundabout$RenderHead = userclient.roundabout$getRoundaboutRenderHead();
            } else {
                if (userclient.roundabout$getRoundaboutRenderChest() != null){
                    userclient.roundabout$setRoundaboutRenderChest(null);
                }
                if (userclient.roundabout$getRoundaboutRenderLegs() != null){
                    userclient.roundabout$setRoundaboutRenderLegs(null);
                }
                if (userclient.roundabout$getRoundaboutRenderBoots() != null){
                    userclient.roundabout$setRoundaboutRenderBoots(null);
                }
                if (userclient.roundabout$getRoundaboutRenderHead() != null){
                    userclient.roundabout$setRoundaboutRenderHead(null);
                }
            }

            if (!((IPlayerEntity)$$3.host).roundabout$getMaskSlot().isEmpty()
                    && ((IPlayerEntity)$$3.host).roundabout$getMaskSlot().getItem() instanceof MaskItem ME
                    && !ME.visageData.rendersArmor()){return;
            }

        } else {
            roundabout$ModifyEntity = false;
        }
        if (ConfigManager.getClientConfig().renderArmorOnPlayerCloneAbilities) {
            this.renderArmorPiece($$0, $$1, $$3, EquipmentSlot.CHEST, $$2, this.getArmorModel(EquipmentSlot.CHEST), $$4, $$5, $$6, $$7, $$8, $$9);
            this.renderArmorPiece($$0, $$1, $$3, EquipmentSlot.LEGS, $$2, this.getArmorModel(EquipmentSlot.LEGS), $$4, $$5, $$6, $$7, $$8, $$9);
            this.renderArmorPiece($$0, $$1, $$3, EquipmentSlot.FEET, $$2, this.getArmorModel(EquipmentSlot.FEET), $$4, $$5, $$6, $$7, $$8, $$9);
            this.renderArmorPiece($$0, $$1, $$3, EquipmentSlot.HEAD, $$2, this.getArmorModel(EquipmentSlot.HEAD), $$4, $$5, $$6, $$7, $$8, $$9);
        }
    }

    private void renderArmorPiece(PoseStack $$0, MultiBufferSource $$1, T entity, EquipmentSlot slot, int $$4, A model,
                                  float limbSwing, float limbSwingAmount, float partialTick,
                                  float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = store(entity.getItemBySlot(slot));
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getEquipmentSlot() == slot) {
                this.getParentModel().copyPropertiesTo2(model);
                this.setPartVisibility(model, slot);
                IPlatformHelperClient platform = ClientPlatform.PLATFORM_ACCESS_CLIENT;
                Model armorModel = platform.getArmorModelHook(entity, itemStack, slot, model);
                if (armorModel == null) {
                    armorModel = model;
                }
                boolean inner = this.usesInnerModel(slot);
                ArmorMaterial material = armorItem.getMaterial().value();
                platform.setupArmorModelAnimations(entity, itemStack, slot, armorModel,
                        limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
                int fallbackColor = platform.getDefaultArmorDyeColor(itemStack);
                for (int layerIndex = 0; layerIndex < material.layers().size(); layerIndex++) {
                    ArmorMaterial.Layer layer = material.layers().get(layerIndex);
                    int color = platform.getArmorLayerTintColor(itemStack, entity, layer, layerIndex, fallbackColor);
                    if (color != 0) {
                        ResourceLocation texture = platform.getArmorTexture(entity, itemStack, layer, inner, slot);
                        this.renderModel($$0, $$1, $$4, armorModel, color, texture);
                    }
                }

                ArmorTrim trim = itemStack.get(DataComponents.TRIM);
                if (trim != null) {
                    this.renderTrim(armorItem.getMaterial(), $$0, $$1, $$4, trim, armorModel, inner);
                }
                if (itemStack.hasFoil()) {
                    this.renderGlint($$0, $$1, $$4, armorModel);
                }
            }
        }
    }

    public ItemStack store(ItemStack stack){
        if (roundabout$ModifyEntity) {
            ItemStack rewrite;
            roundabout$ArmorPhase++;
            if (roundabout$ArmorPhase == 1){
                if (roundabout$RenderChest != null) {
                    return roundabout$RenderChest;
                }
            } else if (roundabout$ArmorPhase == 2){
                if (roundabout$RenderLegs != null) {
                    return roundabout$RenderLegs;
                }
            } else if (roundabout$ArmorPhase == 3){
                if (roundabout$RenderBoots != null) {
                    return roundabout$RenderBoots;
                }
            } else {
                if (roundabout$RenderHead != null) {
                    return roundabout$RenderHead;
                }
            }
            return stack;
        } else {
            return stack;
        }
    }

    protected void setPartVisibility(A $$0, EquipmentSlot $$1) {
        $$0.setAllVisible(false);
        switch ($$1) {
            case HEAD:
                $$0.head.visible = true;
                $$0.hat.visible = true;
                break;
            case CHEST:
                $$0.body.visible = true;
                $$0.rightArm.visible = true;
                $$0.leftArm.visible = true;
                break;
            case LEGS:
                $$0.body.visible = true;
                $$0.rightLeg.visible = true;
                $$0.leftLeg.visible = true;
                break;
            case FEET:
                $$0.rightLeg.visible = true;
                $$0.leftLeg.visible = true;
        }
    }

    private void renderModel(PoseStack pose, MultiBufferSource buffers, int light, Model model,
                             int argbColor, ResourceLocation texture) {
        VertexConsumer vertices = buffers.getBuffer(RenderType.armorCutoutNoCull(texture));
        model.renderToBuffer(pose, vertices, light, OverlayTexture.NO_OVERLAY, argbColor);
    }

    private void renderTrim(Holder<ArmorMaterial> material, PoseStack pose, MultiBufferSource buffers, int light,
                            ArmorTrim trim, Model model, boolean inner) {
        TextureAtlasSprite sprite = this.armorTrimAtlas.getSprite(inner ? trim.innerTexture(material) : trim.outerTexture(material));
        VertexConsumer vertices = sprite.wrap(buffers.getBuffer(Sheets.armorTrimsSheet(trim.pattern().value().decal())));
        model.renderToBuffer(pose, vertices, light, OverlayTexture.NO_OVERLAY);
    }

    private void renderGlint(PoseStack pose, MultiBufferSource buffers, int light, Model model) {
        model.renderToBuffer(pose, buffers.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY);
    }

    private A getArmorModel(EquipmentSlot $$0) {
        return this.usesInnerModel($$0) ? this.innerModel : this.outerModel;
    }

    private boolean usesInnerModel(EquipmentSlot $$0) {
        return $$0 == EquipmentSlot.LEGS;
    }

}
