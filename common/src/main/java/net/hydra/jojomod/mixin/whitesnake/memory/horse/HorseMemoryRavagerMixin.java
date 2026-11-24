package net.hydra.jojomod.mixin.whitesnake.memory.horse;

import net.hydra.jojomod.event.powers.whitesnake.disc.MemoryPersonality;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class HorseMemoryRavagerMixin {
    @Inject(method = "getControllingPassenger", at = @At("HEAD"), cancellable = true)
    private void roundaboutWhitesnake$usePlayerAsController(CallbackInfoReturnable<LivingEntity> cir) {
        if (!((Object) this instanceof Ravager ravager)) return;
        if (!MemoryPersonality.hasHorseMemory(ravager)) return;
        if (ravager.getFirstPassenger() instanceof Player player) cir.setReturnValue(player);
    }
}
