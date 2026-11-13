package net.hydra.jojomod.util;

import java.util.List;
import net.hydra.jojomod.event.ModEffects;
import net.hydra.jojomod.event.index.FateTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class FoodEffects {
    private FoodEffects() {}

    public static boolean blockedByBloodHunger(LivingEntity entity, ItemStack stack) {
        return FateTypes.hasBloodHunger(entity) && !MainUtil.isEdibleToVampires(stack);
    }

    public static boolean blockedByHex(LivingEntity entity, ItemStack stack) {
        var hex = entity.getEffect(ModEffects.HEX);
        if (hex == null) return false;
        int level = hex.getAmplifier();
        return level >= 2 || level >= 0 && stack.is(Items.ENCHANTED_GOLDEN_APPLE)
                || level >= 1 && stack.is(Items.GOLDEN_APPLE);
    }

    public static FoodProperties withoutEffects(FoodProperties food) {
        return new FoodProperties(food.nutrition(), food.saturation(), food.canAlwaysEat(),
                food.eatSeconds(), food.usingConvertsTo(), List.of());
    }
}
