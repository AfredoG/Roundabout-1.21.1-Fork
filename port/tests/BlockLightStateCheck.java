package net.hydra.jojomod.block;

import java.util.List;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/** Initialize the real block definitions and verify both light states, without a world. */
public final class BlockLightStateCheck {
    public static void main(String[] args) {
        net.neoforged.fml.loading.LoadingModList.of(List.of(), List.of(), List.of(), List.of(), Map.of());
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        // Mirror the registration phase, which opens the block registry before mod suppliers run.
        ((net.minecraft.core.MappedRegistry<?>) net.minecraft.core.registries.BuiltInRegistries.BLOCK).unfreeze();
        Block[] blocks = {ModBlocks.FANCY_LIGHTER_PROPRETIES, ModBlocks.CEILING_LIGHT_BLOCK_PROPERTIES,
                ModBlocks.AJA_ORE_PROPERTIES, ModBlocks.DEEPSLATE_AJA_ORE_PROPERTIES, ModBlocks.AJA_BLOCK_PROPERTIES};
        int checks = 0;
        for (Block block : blocks) {
            for (var state : block.getStateDefinition().getPossibleStates()) {
                int light = state.getLightEmission();
                int expected = state.getValue(BlockStateProperties.LIT)
                        ? (block == ModBlocks.FANCY_LIGHTER_PROPRETIES ? 10 : 15) : 0;
                if (light != expected) throw new AssertionError(state + " emits " + light + ", expected " + expected);
                checks++;
            }
        }
        System.out.println("PASS: " + checks + " production block light states");
    }
}
