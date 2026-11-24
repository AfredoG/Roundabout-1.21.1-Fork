package net.hydra.jojomod.mixin.fates;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.hydra.jojomod.access.IMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class FateAbstractHorseMixin {
    @ModifyExpressionValue(method = "getJumpPower(F)F", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    private double roundabout$vampireHorseJump(double strength) {
        return (Object)this instanceof AbstractHorse horse && ((IMob)horse).roundabout$isVampire()
                ? strength * 1.5 : strength;
    }
}
