package net.hydra.jojomod.mixin.time_stop;

import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.event.powers.TimeStop;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Freeze render interpolation without mutating the timer's tick accumulator. */
@Mixin(DeltaTracker.Timer.class)
public abstract class TimeStopDeltaTracker {
    @Unique
    private static boolean roundabout$isScreenFrozen() {
        Minecraft mc = Minecraft.getInstance();
        return mc != null && mc.player != null && mc.level != null
                && ClientUtil.getScreenFreeze()
                && ((TimeStop) mc.level).CanTimeStopEntity(mc.player);
    }

    @Inject(method = "getGameTimeDeltaPartialTick", at = @At("HEAD"), cancellable = true)
    private void roundabout$freezePartialTick(boolean runsNormally, CallbackInfoReturnable<Float> cir) {
        if (roundabout$isScreenFrozen()) cir.setReturnValue(0.0F);
    }

    @Inject(method = "getGameTimeDeltaTicks", at = @At("HEAD"), cancellable = true)
    private void roundabout$freezeDeltaTicks(CallbackInfoReturnable<Float> cir) {
        if (roundabout$isScreenFrozen()) cir.setReturnValue(0.0F);
    }
}
