package net.hydra.jojomod.registry;

import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.particles.HazeColorParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ForgeParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Roundabout.MOD_ID);

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> HIT_IMPACT = PARTICLES.register("hit_impact",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> HALLUCINATORY_ACID_DRIP =
            PARTICLES.register("hallucinatory_acid_drip", () -> new SimpleParticleType(false));
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MELTING = PARTICLES.register("melting",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BLOOD = PARTICLES.register("blood",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> SWEAT = PARTICLES.register("sweat",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BLUE_BLOOD = PARTICLES.register("blue_blood",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> SMALL_EXPLOSION = PARTICLES.register("small_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ENDER_BLOOD = PARTICLES.register("ender_blood",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> POINTER = PARTICLES.register("pointer",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> POINTER_SOFT = PARTICLES.register("pointer_soft",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> AIR_CRACKLE = PARTICLES.register(
            "air_crackle",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> HEARTBEAT = PARTICLES.register(
            "heartbeat",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MUD_SPLASH = PARTICLES.register(
            "mud_splash",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MENACING = PARTICLES.register(
            "menacing",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> VACUUM = PARTICLES.register(
            "vacuum",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> FLASH = PARTICLES.register(
            "flash",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ORANGE_FLAME = PARTICLES.register(
            "orange_flame",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BLUE_FLAME = PARTICLES.register(
            "blue_flame",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PURPLE_FLAME = PARTICLES.register(
            "purple_flame",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> GREEN_FLAME = PARTICLES.register(
            "green_flame",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> DREAD_FLAME = PARTICLES.register(
            "dread_flame",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> CREAM_FLAME = PARTICLES.register(
            "cream_flame",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> FOG_CHAIN = PARTICLES.register(
            "fog_chain",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> STAR = PARTICLES.register(
            "star",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> HEART_ATTACK_MINI = PARTICLES.register(
            "heart_attack_mini",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ENERGY_DISTORTION = PARTICLES.register(
            "energy_distortion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PURPLE_STAR = PARTICLES.register(
            "purple_star",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BLUE_SPARKLE = PARTICLES.register(
            "blue_sparkle",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> UV_SPARKLE = PARTICLES.register(
            "uv_sparkle",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> RED_SPARKLE = PARTICLES.register(
            "red_sparkle",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ICE_SPARKLE = PARTICLES.register(
            "ice_sparkle",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> VAMPIRE_AURA = PARTICLES.register(
            "vampire_aura",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PUNCH_IMPACT_A = PARTICLES.register(
            "punch_impact_a",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PUNCH_MISS = PARTICLES.register(
            "punch_miss",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PUNCH_IMPACT_B = PARTICLES.register(
            "punch_impact_b",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PUNCH_IMPACT_C = PARTICLES.register(
            "punch_impact_c",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> GOLD_PUNCH_IMPACT_A = PARTICLES.register(
            "gold_punch_impact_a",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> GOLD_PUNCH_IMPACT_B = PARTICLES.register(
            "gold_punch_impact_b",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> GOLD_PUNCH_IMPACT_C = PARTICLES.register(
            "gold_punch_impact_c",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> DISC_STEAL_HIT = PARTICLES.register(
            "disc_steal_hit",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BUBBLE_TRAIL = PARTICLES.register(
            "bubble_trail",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> WARDEN_CLOCK = PARTICLES.register(
            "warden_clock",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> CLOCK = PARTICLES.register(
            "clock",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> RED_CLOCK = PARTICLES.register(
            "red_clock",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BLUE_CLOCK = PARTICLES.register(
            "blue_clock",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> GREEN_CLOCK = PARTICLES.register(
            "green_clock",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ORANGE_CLOCK = PARTICLES.register(
            "orange_clock",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> TIME_EMBER = PARTICLES.register(
            "time_ember",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MAGIC_HEART = PARTICLES.register(
            "magic_heart",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> QUESTION = PARTICLES.register(
            "question",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ZAP = PARTICLES.register(
            "zap",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> CINDERELLA_GLOW = PARTICLES.register(
            "cinderella_glow",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PINK_SMOKE = PARTICLES.register(
            "pink_smoke",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BLOOD_MIST = PARTICLES.register(
            "blood_mist",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BUBBLE_POP = PARTICLES.register(
            "soft_bubble_pop",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PLUNDER = PARTICLES.register(
            "plunder",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MENGER = PARTICLES.register(
            "menger",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> FRICTIONLESS = PARTICLES.register(
            "frictionless",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> EXCLAMATION = PARTICLES.register(
            "exclamation",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BABY_CRACKLE = PARTICLES.register(
            "baby_crackle",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> COLD_CRACKLE = PARTICLES.register(
            "cold_crackle",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MAGIC_DUST = PARTICLES.register(
            "magic_dust",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> BRIEF_MAGIC_DUST = PARTICLES.register(
            "brief_magic_dust",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> D4C_LINES = PARTICLES.register(
            "d4c_lines",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> STITCH = PARTICLES.register(
            "stitch",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ZOMBIE_STITCH = PARTICLES.register(
            "zombie_stitch",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> OCCULT = PARTICLES.register(
            "occult",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> AIRBUBBLE_PINK = PARTICLES.register(
            "airbubble_pink",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> AIRBUBBLE_CYAN = PARTICLES.register(
            "airbubble_cyan",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> AIRBUBBLE_GREEN = PARTICLES.register(
            "airbubble_green",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> AIRBUBBLE_YELLOW = PARTICLES.register(
            "airbubble_yellow",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> AIRBUBBLE_BOMB = PARTICLES.register(
            "airbubble_bomb",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ENERGY_RIPPLE_SURFACE = PARTICLES.register(
            "energy_ripple_surface",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ENERGY_RIPPLE = PARTICLES.register(
            "energy_ripple",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> WAKE_RIPPLE = PARTICLES.register(
            "wake_ripple",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> DIVER_DOWN_FINAL = PARTICLES.register(
            "diver_down_final",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> RIBCAGE = PARTICLES.register(
            "ribcage",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> HYPNO_SWIRL = PARTICLES.register(
            "hypno_swirl",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> TUSK_VORTEX = PARTICLES.register(
            "tusk_vortex",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> KILLER_QUEEN_EXPLOSION = PARTICLES.register(
            "killer_queen_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MOLD_DUST = PARTICLES.register(
            "mold_dust",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> MOLD = PARTICLES.register(
            "mold",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PW_FIREBALL_EXPLOSION = PARTICLES.register(
            "pw_fireball_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PW_BLASTWAVE_EXPLOSION = PARTICLES.register(
            "pw_blastwave_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PW_MUSHROOM_EXPLOSION = PARTICLES.register(
            "pw_mushroom_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PW_BLUE_FIREBALL_EXPLOSION = PARTICLES.register(
            "pw_blue_fireball_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PW_BLUE_BLASTWAVE_EXPLOSION = PARTICLES.register(
            "pw_blue_blastwave_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> PW_BLUE_MUSHROOM_EXPLOSION = PARTICLES.register(
            "pw_blue_mushroom_explosion",
            () -> new SimpleParticleType(true)
    );

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, ParticleType<HazeColorParticleOptions>> PURPLE_HAZE_SMOKE =
            PARTICLES.register("purple_haze_smoke", () -> new ParticleType<HazeColorParticleOptions>(false) {
                @Override
                public com.mojang.serialization.MapCodec<HazeColorParticleOptions> codec() {
                    return HazeColorParticleOptions.codec(this);
                }
                @Override
                public net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, HazeColorParticleOptions> streamCodec() {
                    return HazeColorParticleOptions.streamCodec(this);
                }
            });

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> DISTORTION_SMOKE = PARTICLES.register(
            "distortion_smoke",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, ParticleType<HazeColorParticleOptions>> PURPLE_HAZE_BLASTWAVE =
            PARTICLES.register("purple_haze_blastwave", () -> new ParticleType<HazeColorParticleOptions>(false) {
                @Override
                public com.mojang.serialization.MapCodec<HazeColorParticleOptions> codec() {
                    return HazeColorParticleOptions.codec(this);
                }
                @Override
                public net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, HazeColorParticleOptions> streamCodec() {
                    return HazeColorParticleOptions.streamCodec(this);
                }
            });
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> RAGING_LIGHT = PARTICLES.register(
            "raging_light",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ALLURING_LIGHT = PARTICLES.register(
            "alluring_light",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> DUST_CRUMBLE = PARTICLES.register(
            "dust_crumble",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> FIRE_CRUMBLE = PARTICLES.register(
            "fire_crumble",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> SOUL_FIRE_CRUMBLE = PARTICLES.register(
            "soul_fire_crumble",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ROAD_ROLLER_SCRAP = PARTICLES.register(
            "road_roller_scrap",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ROAD_ROLLER_EXPLOSION = PARTICLES.register(
            "road_roller_explosion",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> ROAD_ROLLER_SMOKE = PARTICLES.register(
            "road_roller_smoke",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> TUSK_HOLE = PARTICLES.register(
            "tusk_hole",
            () -> new SimpleParticleType(true)
    );
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> GOLDEN_SPIRAL = PARTICLES.register(
            "golden_spiral",
            () -> new SimpleParticleType(true)
    );

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_FIELD_PNG = PARTICLES.register("metallica_field_png", () -> new SimpleParticleType(true));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_NAIL = PARTICLES.register("nail",
            () -> new SimpleParticleType(true));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_RAZOR = PARTICLES.register("razor",
            () -> new SimpleParticleType(true));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_SCISSORS = PARTICLES.register("scissors",
            () -> new SimpleParticleType(true));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_A = PARTICLES.register(
            "metallica_a", () -> new SimpleParticleType(false));
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_B = PARTICLES.register(
            "metallica_b", () -> new SimpleParticleType(false));
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_C = PARTICLES.register(
            "metallica_c", () -> new SimpleParticleType(false));
    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> METALLICA_D = PARTICLES.register(
            "metallica_d", () -> new SimpleParticleType(false));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> TOOTH = PARTICLES.register("tooth",
            () -> new SimpleParticleType(true));

    public static final DeferredHolder<net.minecraft.core.particles.ParticleType<?>, SimpleParticleType> VITAL_ESSENCE = PARTICLES.register("vital_essence",
            () -> new SimpleParticleType(true)
    );
}
