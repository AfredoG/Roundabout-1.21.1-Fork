package net.hydra.jojomod.util;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** Bridges upstream block interactions to the two block-use phases in 1.21.1. */
public final class BlockInteractionCompat {
    private BlockInteractionCompat() {}

    public static ItemInteractionResult fromLegacyResult(InteractionResult result) {
        return switch (result) {
            // The item phase has no separate SUCCESS_NO_ITEM_USED result. The
            // useWithoutItem callback retains that result without conversion.
            case SUCCESS, SUCCESS_NO_ITEM_USED -> ItemInteractionResult.SUCCESS;
            case CONSUME -> ItemInteractionResult.CONSUME;
            case CONSUME_PARTIAL -> ItemInteractionResult.CONSUME_PARTIAL;
            // The shared handler already ran: do not invoke it a second time in
            // useWithoutItem. Minecraft can still try the held item's useOn.
            case PASS -> ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            case FAIL -> ItemInteractionResult.FAIL;
        };
    }

    /** A single explicit block action from a stand, without also using the held item. */
    public static InteractionResult use(BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        BlockHitResult target = hit.withPosition(pos);
        ItemInteractionResult result = state.useItemOn(player.getItemInHand(hand), level, player, hand, target);
        // Unlike a full player click, a stand invokes this once for the supplied
        // hand. Default vanilla blocks must also work for an offhand invocation.
        return resolve(result, () -> state.useWithoutItem(level, player, target));
    }

    static InteractionResult resolve(ItemInteractionResult result, Supplier<InteractionResult> defaultUse) {
        return result == ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
                ? defaultUse.get() : result.result();
    }
}
