package net.hydra.jojomod.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/** Applies durability without consuming the stack; the caller decides how to handle breakage. */
public final class ItemDurability {
    private ItemDurability() {}
    public static boolean damageWithoutBreaking(ItemStack stack, int amount, ServerPlayer player) {
        return damageWithoutBreaking(stack, amount, player.serverLevel(), player);
    }

    public static boolean damageWithoutBreaking(ItemStack stack, int amount,
            net.minecraft.server.level.ServerLevel level, @javax.annotation.Nullable ServerPlayer player) {
        if (!stack.isDamageableItem()) return false;
        if (amount > 0) {
            amount = EnchantmentHelper.processDurabilityChange(level, stack, amount);
            if (amount <= 0) return false;
        }
        int damage = stack.getDamageValue() + amount;
        if (amount != 0 && player != null) CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(player, stack, damage);
        stack.setDamageValue(damage);
        return damage >= stack.getMaxDamage();
    }
}
