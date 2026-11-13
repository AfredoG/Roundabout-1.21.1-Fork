package net.hydra.jojomod.mixin.pearl_jam;

import net.hydra.jojomod.util.MainUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class EnergizedFoodLoreTextMixin {

    @Inject(method = "appendHoverText", at = @At(value = "TAIL"))
    public void appendFoodLore(ItemStack $$0, net.minecraft.world.item.Item.TooltipContext $$1, List<Component> $$2, TooltipFlag $$3, CallbackInfo ci){
        if (net.hydra.jojomod.util.ItemData.has($$0)){
            if (net.hydra.jojomod.util.ItemData.copy($$0).contains("pearljamfood")){
                if (MainUtil.foodCuresThat.containsKey($$0.getItem()) || MainUtil.foodAddsThat.containsKey($$0.getItem()) || MainUtil.specialFoodRemoves.containsKey($$0.getItem())){
                    if (MainUtil.foodCuresThat.containsKey($$0.getItem())) {
                        List<net.minecraft.core.Holder<MobEffect>> effects = MainUtil.foodCuresThat.get($$0.getItem());
                        for (net.minecraft.core.Holder<MobEffect> effect : effects) {
                            $$2.add(Component.translatable("text.roundabout.pearl_jam.cure_lore", Component.translatable(effect.value().getDescriptionId()).withStyle(ChatFormatting.GOLD)));
                        }
                    }
                    if (MainUtil.foodAddsThat.containsKey($$0.getItem())) {
                        List<net.minecraft.core.Holder<MobEffect>> effects = MainUtil.foodAddsThat.get($$0.getItem());
                        for (net.minecraft.core.Holder<MobEffect> effect : effects) {
                            $$2.add(Component.translatable("text.roundabout.pearl_jam.add_lore", Component.translatable(effect.value().getDescriptionId()).withStyle(ChatFormatting.GOLD)));
                        }
                    }
                    if (MainUtil.specialFoodRemoves.containsKey($$0.getItem())) {
                        List<String> conditions = MainUtil.specialFoodRemoves.get($$0.getItem());
                        for (String condition : conditions) {
                            $$2.add(Component.translatable("text.roundabout.pearl_jam.remove_lore", Component.translatable("text.roundabout.condition.".concat(condition)).withStyle(ChatFormatting.GOLD)));
                        }
                    }
                } else {
                    $$2.add(Component.translatable("text.roundabout.pearl_jam.cure_lore", Component.translatable(MainUtil.pearlJamFallback.value().getDescriptionId()).withStyle(ChatFormatting.GOLD)));
                }
            }
        }
    }
}
