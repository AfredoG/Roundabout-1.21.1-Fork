package net.hydra.jojomod.util;

import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;

/** Standalone regression check against the mapped Minecraft/NeoForge classes. */
public final class BlockInteractionCompatCheck {
    public static void main(String[] args) {
        int cases = 0;
        for (InteractionResult original : InteractionResult.values()) {
            AtomicInteger calls = new AtomicInteger();
            calls.incrementAndGet(); // The upstream interaction has already run.
            ItemInteractionResult mapped = BlockInteractionCompat.fromLegacyResult(original);
            InteractionResult actual = BlockInteractionCompat.resolve(mapped, () -> {
                calls.incrementAndGet();
                return original;
            });
            require(calls.get() == 1, "duplicate block action: " + original);
            require(actual.consumesAction() == original.consumesAction(), "consumption: " + original);
            require(actual.shouldSwing() == original.shouldSwing(), "swing: " + original);
            if (original != InteractionResult.SUCCESS_NO_ITEM_USED) {
                require(actual == original, "result: " + original);
            }
            if (original == InteractionResult.PASS) {
                require(mapped == ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION, "PASS fallback");
            }
            cases++;
        }
        for (ItemInteractionResult phase : ItemInteractionResult.values()) {
            for (InteractionResult fallback : InteractionResult.values()) {
                AtomicInteger calls = new AtomicInteger();
                InteractionResult actual = BlockInteractionCompat.resolve(phase, () -> {
                    calls.incrementAndGet();
                    return fallback;
                });
                boolean useDefault = phase == ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                require(calls.get() == (useDefault ? 1 : 0), "default calls: " + phase);
                require(actual == (useDefault ? fallback : phase.result()), "default result: " + phase);
                cases++;
            }
        }
        System.out.println("PASS: " + cases + " block interaction cases");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
