package net.hydra.jojomod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/** Shared registry lookups and server-side enchantment effects for custom attacks. */
public final class EnchantmentCompat {
    private EnchantmentCompat() {}

    public static int itemLevel(ResourceKey<Enchantment> key, ItemStack stack, Level level) {
        return EnchantmentHelper.getItemEnchantmentLevel(
                level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key), stack);
    }

    public static int equipmentLevel(ResourceKey<Enchantment> key, LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(
                entity.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key), entity);
    }

    public static float modifyDamage(Level level, ItemStack weapon, Entity target, DamageSource source, float damage) {
        return level instanceof ServerLevel serverLevel
                ? EnchantmentHelper.modifyDamage(serverLevel, weapon, target, source, damage) : damage;
    }

    public static void postAttack(Level level, Entity target, DamageSource source, @Nullable ItemStack weapon) {
        if (level instanceof ServerLevel serverLevel) {
            EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, target, source, weapon);
        }
    }

    public static int tridentReturnAcceleration(ItemStack stack, Entity trident) {
        return trident.level() instanceof ServerLevel serverLevel
                ? EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverLevel, stack, trident) : 0;
    }
}
