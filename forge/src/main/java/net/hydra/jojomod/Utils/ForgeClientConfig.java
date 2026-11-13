package net.hydra.jojomod.Utils;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ForgeClientConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("Configs for Tutorial Mod");

        // HERE DEFINE YOUR CONFIGS

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
