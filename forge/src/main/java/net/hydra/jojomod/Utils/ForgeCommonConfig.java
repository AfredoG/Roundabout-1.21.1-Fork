package net.hydra.jojomod.Utils;

import net.neoforged.neoforge.common.ModConfigSpec;
public class ForgeCommonConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Boolean> CAN_BREATHE_IN_TS;

    static {
        BUILDER.push("Configs for Tutorial Mod");

        CAN_BREATHE_IN_TS = BUILDER.comment("Whether or not your oxygen decreases in time stop. Canonically, you couldn't breathe. But breathing is on by default to prevent confusion and clutter.")
                .define("Can Breathe in TS", false);
        /**CITRINE_ORE_VEIN_SIZE = BUILDER.comment("How many Citrine Ore Blocks spawn in one Vein!")
                .defineInRange("Vein Size", 9, 4, 20);
         */

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
