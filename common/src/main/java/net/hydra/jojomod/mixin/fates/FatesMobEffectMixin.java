package net.hydra.jojomod.mixin.fates;

import net.hydra.jojomod.event.index.FateTypes;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.effect.HungerMobEffect")
public abstract class FatesMobEffectMixin {
    @Inject(method = "applyEffectTick", at = @At("HEAD"), cancellable = true)
    private void roundabout$removeVampireHunger(LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if (!entity.level().isClientSide() && FateTypes.hasBloodHunger(entity)) {
            // MobEffectInstance removes effects whose tick returns false.
            cir.setReturnValue(false);
        }
    }
}
