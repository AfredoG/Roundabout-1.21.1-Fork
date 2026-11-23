package net.hydra.jojomod.mixin.century_boy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.stand.powers.Powers20thCenturyBoy;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffectInstance.class)
public abstract class CenturyBoyPotion {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/effect/MobEffect;applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)Z"))
    private boolean roundabout$cancelEffectTick(MobEffect effect, LivingEntity entity, int amplifier,
            Operation<Boolean> original) {
        if (effect != MobEffects.WITHER.value() && entity instanceof StandUser user
                && user.roundabout$getStandPowers() instanceof Powers20thCenturyBoy powers && powers.invincibleState) {
            // Skip this application, but retain the effect and its normal duration countdown.
            return true;
        }
        return original.call(effect, entity, amplifier);
    }
}
