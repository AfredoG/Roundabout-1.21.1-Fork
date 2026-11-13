package net.hydra.jojomod.mixin.gravity;

import net.hydra.jojomod.item.ModItems;
import net.hydra.jojomod.util.gravity.GravityAPI;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class GravityCrossbowItemMixin {
    @Inject(method = "getChargeDuration", at = @At("HEAD"), cancellable = true)
    private static void roundabout$getChargeDuration(ItemStack weapon, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        if (weapon.is(ModItems.IRON_BALL_CROSSBOW)) {
            int quickCharge = weapon.getEnchantmentLevel(user.registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.QUICK_CHARGE));
            cir.setReturnValue(quickCharge == 0 ? 28 : 28 - 2 * quickCharge);
        }
    }

    // The advancement is reached only after a non-empty charge was actually fired.
    @Inject(method = "performShooting", at = @At(value = "FIELD",
            target = "Lnet/minecraft/advancements/CriteriaTriggers;SHOT_CROSSBOW:Lnet/minecraft/advancements/critereon/ShotCrossbowTrigger;"))
    private void roundabout$ironBallCooldown(Level level, LivingEntity user, InteractionHand hand, ItemStack weapon,
            float velocity, float accuracy, LivingEntity target, CallbackInfo ci) {
        if (user instanceof ServerPlayer player && !player.isCreative() && weapon.is(ModItems.IRON_BALL_CROSSBOW)) {
            player.getCooldowns().addCooldown(weapon.getItem(), 80);
        }
    }

    // Shooting, multishot, durability and sounds remain in the native pipeline.
    @Inject(method = "createProjectile", at = @At("RETURN"))
    private void roundabout$fireworkOrigin(Level level, LivingEntity user, ItemStack weapon, ItemStack ammo,
            boolean critical, CallbackInfoReturnable<Projectile> cir) {
        Direction gravity = GravityAPI.getGravityDirection(user);
        if (gravity != Direction.DOWN && cir.getReturnValue() instanceof FireworkRocketEntity rocket) {
            Vec3 origin = user.getEyePosition().subtract(RotationUtil.vecPlayerToWorld(0.0D, 0.15D, 0.0D, gravity));
            rocket.setPos(origin.x, origin.y, origin.z);
        }
    }
}
