package net.hydra.jojomod.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/** Stand arrows remain in the inventory when exhausted, as in the upstream mod. */
public final class ItemDurability {
    private ItemDurability() {}
    public static boolean damageWithoutBreaking(ItemStack stack, int amount, ServerPlayer player) {
        if (!stack.isDamageableItem()) return false;
        if (amount > 0) {
            amount = EnchantmentHelper.processDurabilityChange(player.serverLevel(), stack, amount);
            if (amount <= 0) return false;
        }
        int damage = stack.getDamageValue() + amount;
        if (amount != 0) CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(player, stack, damage);
        stack.setDamageValue(damage);
        return damage >= stack.getMaxDamage();
    }
}
