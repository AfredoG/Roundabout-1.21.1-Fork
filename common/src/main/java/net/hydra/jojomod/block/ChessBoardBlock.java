package net.hydra.jojomod.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.ItemInteractionResult;
import net.hydra.jojomod.util.BlockInteractionCompat;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.hydra.jojomod.Roundabout;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Map;

public class ChessBoardBlock extends Block implements EntityBlock {
	public ChessBoardBlock(Properties $$0) {
        super($$0);
    }
	
	@Override
    public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
        return new ChessBoardBlockEntity($$0, $$1);
    }
	
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        return BlockInteractionCompat.fromLegacyResult(use(state, level, pos, player, hand, hit));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        return use(state, level, pos, player, InteractionHand.MAIN_HAND, hit);
    }

    // Keep the upstream interaction in one shared handler for both phases.
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return InteractionResult.SUCCESS;
	}
	
}