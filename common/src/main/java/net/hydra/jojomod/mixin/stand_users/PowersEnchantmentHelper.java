package net.hydra.jojomod.mixin.stand_users;

import net.hydra.jojomod.event.powers.StandUser;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class PowersEnchantmentHelper {

    /**Stands like white album can make increased knockback apply to weapon attacks*/
    @Inject(method = "modifyKnockback", at = @At("RETURN"), cancellable = true)
    private static void roundabout$getKnockbackBonus(ServerLevel level, ItemStack weapon, Entity target,
            DamageSource source, float baseKnockback, CallbackInfoReturnable<Float> cir) {
        if (source.getEntity() instanceof LivingEntity attacker && source.getDirectEntity() == attacker){
            int knockback = ((StandUser)attacker).roundabout$getStandPowers().bufferAttackKnockbackLevel();
            if (knockback > 0){
                cir.setReturnValue(Math.max(cir.getReturnValue(), baseKnockback + knockback));
            }
        }
    }
}
