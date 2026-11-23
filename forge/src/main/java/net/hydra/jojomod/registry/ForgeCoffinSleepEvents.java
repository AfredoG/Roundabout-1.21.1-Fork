package net.hydra.jojomod.registry;

import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.block.CoffinBlock;
import net.minecraft.world.entity.player.Player.BedSleepingProblem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

@EventBusSubscriber(modid = Roundabout.MOD_ID)
public final class ForgeCoffinSleepEvents {
    private ForgeCoffinSleepEvents() {}

    @SubscribeEvent
    public static void startSleeping(CanPlayerSleepEvent event) {
        if (!(event.getState().getBlock() instanceof CoffinBlock)) return;
        // Change the time rule while preserving failures such as distance, obstruction and dimension.
        if (event.getProblem() == null || event.getProblem() == BedSleepingProblem.NOT_POSSIBLE_NOW) {
            boolean canSleep = event.getLevel().isDay() || event.getLevel().isThundering();
            event.setProblem(canSleep ? null : BedSleepingProblem.NOT_POSSIBLE_NOW);
        }
    }

    @SubscribeEvent
    public static void continueSleeping(CanContinueSleepingEvent event) {
        var entity = event.getEntity();
        var pos = entity.getSleepingPos();
        if (pos.isEmpty() || !(entity.level().getBlockState(pos.get()).getBlock() instanceof CoffinBlock)) return;
        if (event.getProblem() == null || event.getProblem() == BedSleepingProblem.NOT_POSSIBLE_NOW) {
            event.setContinueSleeping(entity.level().isDay() || entity.level().isThundering());
        }
    }
}
