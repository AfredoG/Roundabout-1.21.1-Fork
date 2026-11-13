package net.hydra.jojomod.mixin;

import net.hydra.jojomod.event.ModEffects;
import net.hydra.jojomod.event.powers.whitesnake.HallucinationEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class HallucinationEffectLifecycleMixin {
    @Inject(method = "onEffectAdded", at = @At("TAIL"))
    private void roundabout$hallucinationAdded(MobEffectInstance effect, @Nullable Entity source, CallbackInfo ci) {
        if (effect.is(ModEffects.HALLUCINATION)) {
            HallucinationEffect.onLevelChanged((LivingEntity) (Object) this, effect.getAmplifier());
        }
    }

    @Inject(method = "onEffectUpdated", at = @At("TAIL"))
    private void roundabout$hallucinationUpdated(MobEffectInstance effect, boolean reapply,
                                                @Nullable Entity source, CallbackInfo ci) {
        if (reapply && effect.is(ModEffects.HALLUCINATION)) {
            HallucinationEffect.onLevelChanged((LivingEntity) (Object) this, effect.getAmplifier());
        }
    }

    @Inject(method = "onEffectRemoved", at = @At("TAIL"))
    private void roundabout$hallucinationRemoved(MobEffectInstance effect, CallbackInfo ci) {
        if (effect.is(ModEffects.HALLUCINATION)) {
            HallucinationEffect.onRemoved((LivingEntity) (Object) this);
        }
    }
}
