package net.hydra.jojomod.util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

/** Centralizes the item-component migration while retaining upstream call shapes. */
public final class PotionData {
    private PotionData() {}
    public static PotionContents contents(ItemStack stack) {
        return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }
    @Nullable public static Holder<Potion> getPotion(ItemStack stack) {
        return contents(stack).potion().orElse(null);
    }
    public static boolean isPotion(ItemStack stack, Holder<Potion> potion) {
        return contents(stack).potion().map(value -> value.is(potion)).orElse(false);
    }
    public static ItemStack setPotion(ItemStack stack, Holder<Potion> potion) {
        stack.set(DataComponents.POTION_CONTENTS, contents(stack).withPotion(potion));
        return stack;
    }
    public static List<MobEffectInstance> getMobEffects(ItemStack stack) {
        List<MobEffectInstance> effects = new ArrayList<>();
        contents(stack).forEachEffect(effects::add);
        return effects;
    }
    public static List<MobEffectInstance> getCustomEffects(ItemStack stack) {
        return contents(stack).customEffects().stream().map(MobEffectInstance::new).toList();
    }
    public static int getColor(ItemStack stack) { return contents(stack).getColor(); }
}
