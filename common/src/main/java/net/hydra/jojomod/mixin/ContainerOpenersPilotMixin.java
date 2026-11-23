package net.hydra.jojomod.mixin;

import java.util.ArrayList;
import java.util.List;
import net.hydra.jojomod.entity.stand.StandEntity;
import net.hydra.jojomod.event.powers.StandPowers;
import net.hydra.jojomod.event.powers.StandUser;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerOpenersCounter.class)
public abstract class ContainerOpenersPilotMixin {
    @Shadow protected abstract boolean isOwnContainer(Player player);

    @Inject(method = "getPlayersWithContainerOpen", at = @At("RETURN"), cancellable = true)
    private void roundabout$addStandOpeners(Level level, BlockPos pos, CallbackInfoReturnable<List<Player>> cir) {
        List<Player> openers = new ArrayList<>(cir.getReturnValue());
        for (Player player : level.players()) {
            if (!openers.contains(player) && isOwnContainer(player) && player instanceof StandUser user) {
                StandPowers powers = user.roundabout$getStandPowers();
                if (powers != null && powers.isPiloting()) {
                    StandEntity stand = powers.getStandEntity(player);
                    if (stand != null && stand.isAlive() && stand.level() == level
                            && stand.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0D) {
                        openers.add(player);
                    }
                }
            }
        }
        cir.setReturnValue(openers);
    }
}
