package net.hydra.jojomod.mixin.time_stop;

import com.mojang.blaze3d.vertex.PoseStack;
import net.hydra.jojomod.access.IPlayerEntity;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.event.index.ShapeShifts;
import net.hydra.jojomod.event.powers.StandUserClient;
import net.hydra.jojomod.event.powers.TimeStop;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class TimeStopHumanoidArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    /**This class is targetted so the last piece of equipment worn before a timestop is rendered in the timestop*/

    @Unique
    public boolean roundabout$ModifyEntity;
    @Unique
    public @Nullable ItemStack roundabout$RenderChest;
    @Unique
    public @Nullable ItemStack roundabout$RenderLegs;
    @Unique
    public @Nullable ItemStack roundabout$RenderBoots;
    @Unique
    public @Nullable ItemStack roundabout$RenderHead;


    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "HEAD"),cancellable = true)
    public void roundabout$Render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, CallbackInfo ci) {
        StandUserClient userclient = ((StandUserClient) $$3);

        if ($$3 instanceof Player PE) {
            IPlayerEntity ipe = ((IPlayerEntity) PE);
            if (ShapeShifts.getShiftFromByte(ipe.roundabout$getShapeShift()) == ShapeShifts.EERIE ||
                    ShapeShifts.getShiftFromByte(ipe.roundabout$getShapeShift()) == ShapeShifts.OVA){
                ci.cancel();
                return;
            }

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
        } else {
            roundabout$ModifyEntity = false;
        }
    }
    @com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation(
            method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack roundabout$frozenArmor(LivingEntity entity, EquipmentSlot slot,
            com.llamalad7.mixinextras.injector.wrapoperation.Operation<ItemStack> original) {
        ItemStack current = original.call(entity, slot);
        if (!roundabout$ModifyEntity) return current;
        ItemStack frozen = switch (slot) {
            case CHEST -> roundabout$RenderChest;
            case LEGS -> roundabout$RenderLegs;
            case FEET -> roundabout$RenderBoots;
            case HEAD -> roundabout$RenderHead;
            default -> null;
        };
        return frozen != null ? frozen : current;
    }

    /**Shadows, ignore
     * -------------------------------------------------------------------------------------------------------------
     * */

    @Shadow
    public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {

    }


    public TimeStopHumanoidArmorLayer(RenderLayerParent<T, M> $$0) {
        super($$0);
    }
}
