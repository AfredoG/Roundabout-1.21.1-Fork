package net.hydra.jojomod.mixin.metallica;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.stand.powers.PowersMetallica;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TrapDoorBlock.class)
public abstract class MetallicaIronTrapdoorMixin {
    // Keep native sound, state updates and (for trapdoors) water scheduling.
    @ModifyExpressionValue(method = "useWithoutItem", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/properties/BlockSetType;canOpenByHand()Z"))
    private boolean roundabout$allowMetallica(boolean original, BlockState state, Level level,
                                             BlockPos pos, Player player, BlockHitResult hit) {
        return original || (state.is(Blocks.IRON_TRAPDOOR)
                && ((StandUser) player).roundabout$getStandPowers() instanceof PowersMetallica);
    }
}
