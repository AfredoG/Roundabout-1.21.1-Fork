package net.hydra.jojomod.item;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.hydra.jojomod.entity.projectile.IronBallEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;

public class IronBallCrossbowItem extends CrossbowItem {
    public IronBallCrossbowItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(Items.IRON_INGOT);
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return getAllSupportedProjectiles();
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack weapon) {
        return new ItemStack(Items.IRON_INGOT);
    }

    @Override
    public void releaseUsing(ItemStack weapon, Level level, LivingEntity user, int remainingTicks) {
        int usedTicks = getUseDuration(weapon, user) - remainingTicks;
        if (usedTicks < getChargeDuration(weapon, user) || isCharged(weapon)) return;
        ItemStack ammo = user.getProjectile(weapon);
        if (ammo.isEmpty() && user.hasInfiniteMaterials()) ammo = getDefaultCreativeAmmo(null, weapon);
        // Vanilla draw applies projectile-count/ammo-use enchantment effects and consumes only the primary shot.
        List<ItemStack> projectiles = draw(weapon, ammo, user);
        if (projectiles.isEmpty()) return;
        weapon.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(projectiles));
        level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CROSSBOW_LOADING_END,
                user instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE, 1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity user, ItemStack weapon, ItemStack ammo, boolean critical) {
        IronBallEntity ball = new IronBallEntity(level, user, ammo, weapon);
        ball.setCritArrow(critical);
        ball.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        return ball;
    }
}
