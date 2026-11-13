package net.hydra.jojomod.registry;

import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.block.*;
import net.hydra.jojomod.block.handBlock.HandBlockEntity;
import net.hydra.jojomod.item.FogBlockItem;
import net.hydra.jojomod.item.FogCoatBlockItem;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;

import static net.hydra.jojomod.block.ModBlocks.*;
import static net.hydra.jojomod.registry.ForgeCreativeTab.FOG_TAB_ITEMS;
import static net.hydra.jojomod.registry.ForgeCreativeTab.addToFogTab;
import static net.hydra.jojomod.registry.ForgeItems.ITEMS;

@EventBusSubscriber(modid = Roundabout.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ForgeBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Roundabout.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Roundabout.MOD_ID);
    public static final DeferredHolder<net.minecraft.world.level.block.Block, HallucinatoryAcidBlock> HALLUCINATORY_ACID = BLOCKS.register(
            "hallucinatory_acid", () -> new HallucinatoryAcidBlock(BlockBehaviour.Properties.of()
                    .noCollission().noOcclusion().strength(1.0F, 6.0F).noLootTable().randomTicks()
                    .sound(SoundType.MUD).pushReaction(PushReaction.BLOCK)));
    public static final DeferredHolder<net.minecraft.world.level.block.Block, HallucinatoryAcidWallBlock> HALLUCINATORY_ACID_WALL = BLOCKS.register(
            "hallucinatory_acid_wall", () -> new HallucinatoryAcidWallBlock(BlockBehaviour.Properties.of()
                    .noCollission().noOcclusion().strength(1.0F, 6.0F).noLootTable().randomTicks()
                    .sound(SoundType.MUD).pushReaction(PushReaction.BLOCK)));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<HallucinatoryAcidBlockEntity>> HALLUCINATORY_ACID_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("hallucinatory_acid", () -> BlockEntityType.Builder.of(
                    HallucinatoryAcidBlockEntity::new, HALLUCINATORY_ACID.get(), HALLUCINATORY_ACID_WALL.get())
                    .build(null));

    public static final DeferredHolder<Block, Block> ANCIENT_METEOR = BLOCKS.register("ancient_meteor",
            () -> ModBlocks.ANCIENT_METEOR_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> AJA_ORE = BLOCKS.register("aja_ore",
            () -> ModBlocks.AJA_ORE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> DEEPSLATE_AJA_ORE = BLOCKS.register("deepslate_aja_ore",
            () -> ModBlocks.DEEPSLATE_AJA_ORE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> AJA_BLOCK = BLOCKS.register("aja_block",
            () -> AJA_BLOCK_PROPERTIES
    );

    public static final DeferredHolder<Block, Block> CHESS_PIECE = BLOCKS.register("chess_piece",
            () -> ModBlocks.getChessBlock()
    );
    public static final DeferredHolder<Block, Block> CLIMBING_WIRE = BLOCKS.register("climbing_wire",
            () -> CLIMBING_WIRE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_DIRT = BLOCKS.register("frozen_dirt",
            () -> FROZEN_DIRT_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_STONE = BLOCKS.register("frozen_stone",
            () -> FROZEN_STONE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_COBBLESTONE = BLOCKS.register("frozen_cobblestone",
            () -> FROZEN_COBBLESTONE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_DIORITE = BLOCKS.register("frozen_diorite",
            () -> FROZEN_DIORITE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_ANDESITE = BLOCKS.register("frozen_andesite",
            () -> FROZEN_ANDESITE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_GRANITE = BLOCKS.register("frozen_granite",
            () -> FROZEN_GRANITE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_DEEPSLATE = BLOCKS.register("frozen_deepslate",
            () -> FROZEN_DEEPSLATE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_COBBLED_DEEPSLATE =
            BLOCKS.register("frozen_cobbled_deepslate",
            () -> FROZEN_COBBLED_DEEPSLATE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> FROZEN_STONE_BRICKS =
            BLOCKS.register("frozen_stone_bricks",
                    () -> FROZEN_STONE_BRICKS_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_SAND =
            BLOCKS.register("frozen_sand",
                    () -> FROZEN_SAND_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_SANDSTONE =
            BLOCKS.register("frozen_sandstone",
                    () -> FROZEN_SANDSTONE_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_RED_SAND =
            BLOCKS.register("frozen_red_sand",
                    () -> FROZEN_RED_SAND_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_RED_SANDSTONE =
            BLOCKS.register("frozen_red_sandstone",
                    () -> FROZEN_RED_SANDSTONE_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_GRAVEL =
            BLOCKS.register("frozen_gravel",
                    () -> FROZEN_GRAVEL_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_OBSIDIAN =
            BLOCKS.register("frozen_obsidian",
                    () -> FROZEN_OBSIDIAN_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_NETHERRACK =
            BLOCKS.register("frozen_netherrack",
                    () -> FROZEN_NETHERRACK_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_NETHER_BRICKS =
            BLOCKS.register("frozen_nether_bricks",
                    () -> FROZEN_NETHER_BRICKS_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FROZEN_END_STONE =
            BLOCKS.register("frozen_end_stone",
                    () -> FROZEN_END_STONE_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> COLD_AIR =
            BLOCKS.register("cold_air",
                    () -> COLD_AIR_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> FREEZING_AIR =
            BLOCKS.register("freezing_air",
                    () -> FREEZING_AIR_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> METEOR_BLOCK = BLOCKS.register("meteor_block",
            () -> ModBlocks.METEOR_BLOCK_PROPERTIES
            );
    public static final DeferredHolder<Block, Block> SHINY_QUARTZ = BLOCKS.register("shiny_quartz",
            () -> SHINY_QUARTZ_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WHITE_ALBUM_ICE = BLOCKS.register("white_album_ice",
            () -> WHITE_ALBUM_ICE_BLOCK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WHITE_ALBUM_ICE_WALL = BLOCKS.register("white_album_ice_wall",
            () -> WHITE_ALBUM_ICE_WALL_BLOCK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WHITE_ALBUM_ICE_SLAB = BLOCKS.register("white_album_coating",
            () -> WHITE_ALBUM_COATING_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> STICKY_ICE_BLOCK = BLOCKS.register("sticky_ice",
            () -> STICKY_ICE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> ICE_SPIKE = BLOCKS.register("ice_spike",
            () -> ICE_SPIKE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> SHINY_QUARTZ_TILES = BLOCKS.register("shiny_quartz_tiles",
            () -> SHINY_QUARTZ_TILES_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> IMPACT_MOUND = BLOCKS.register("impact_mound",
            () -> IMPACT_MOUND_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> EQUIPPABLE_STONE_MASK_BLOCK = BLOCKS.register("stone_mask",
            () -> ModBlocks.EQUIPPABLE_STONE_MASK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> BLOODY_STONE_MASK_BLOCK = BLOCKS.register("bloody_stone_mask",
            () -> BLOODY_STONE_MASK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> COFFIN_BLOCK = BLOCKS.register("coffin_block",
            () -> COFFIN_BLOCK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> KING_BED_BLOCK = BLOCKS.register("king_bed_block",
            () -> KING_BED_BLOCK_PROPERTIES
    );
    /*public static final DeferredHolder<Block, Block> CHESSBOARD_BLOCK = BLOCKS.register("chessboard_block",
            () -> CHESSBOARD_BLOCK_PROPERTIES
    );*/
    public static final DeferredHolder<Block, Block> REGAL_WALL = BLOCKS.register("regal_wall",
            () -> ModBlocks.REGAL_WALL_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> REGAL_FLOOR = BLOCKS.register("regal_floor",
            () -> ModBlocks.REGAL_FLOOR_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOODEN_MANOR_TABLE = BLOCKS.register("wooden_manor_table",
            () -> ModBlocks.WOODEN_MANOR_TABLE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOODEN_MANOR_CHAIR = BLOCKS.register("wooden_manor_chair",
            () -> ModBlocks.WOODEN_MANOR_CHAIR_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_WHITE = BLOCKS.register("wool_slab_white",
            () -> ModBlocks.WOOL_SLAB_WHITE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_BLACK = BLOCKS.register("wool_slab_black",
            () -> ModBlocks.WOOL_SLAB_BLACK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_RED = BLOCKS.register("wool_slab_red",
            () -> ModBlocks.WOOL_SLAB_RED_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_LIGHT_GREY = BLOCKS.register("wool_slab_light_grey",
            () -> ModBlocks.WOOL_SLAB_LIGHT_GREY_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_BROWN = BLOCKS.register("wool_slab_brown",
            () -> ModBlocks.WOOL_SLAB_BROWN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_BLUE = BLOCKS.register("wool_slab_blue",
            () -> ModBlocks.WOOL_SLAB_BLUE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_CYAN = BLOCKS.register("wool_slab_cyan",
            () -> ModBlocks.WOOL_SLAB_CYAN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_DARK_GREEN = BLOCKS.register("wool_slab_dark_green",
            () -> ModBlocks.WOOL_SLAB_DARK_GREEN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_DARK_GREY = BLOCKS.register("wool_slab_dark_grey",
            () -> ModBlocks.WOOL_SLAB_DARK_GREY_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_GREEN = BLOCKS.register("wool_slab_green",
            () -> ModBlocks.WOOL_SLAB_GREEN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_LIGHT_BLUE = BLOCKS.register("wool_slab_light_blue",
            () -> ModBlocks.WOOL_SLAB_LIGHT_BLUE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_MAGENTA = BLOCKS.register("wool_slab_magenta",
            () -> ModBlocks.WOOL_SLAB_MAGENTA_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_ORANGE = BLOCKS.register("wool_slab_orange",
            () -> ModBlocks.WOOL_SLAB_ORANGE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_PURPLE = BLOCKS.register("wool_slab_purple",
            () -> ModBlocks.WOOL_SLAB_PURPLE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_PINK = BLOCKS.register("wool_slab_pink",
            () -> ModBlocks.WOOL_SLAB_PINK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_SLAB_YELLOW = BLOCKS.register("wool_slab_yellow",
            () -> ModBlocks.WOOL_SLAB_YELLOW_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_WHITE = BLOCKS.register("wool_stairs_white",
            () -> ModBlocks.WOOL_STAIRS_WHITE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_BLUE = BLOCKS.register("wool_stairs_blue",
            () -> ModBlocks.WOOL_STAIRS_BLUE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_CYAN = BLOCKS.register("wool_stairs_cyan",
            () -> ModBlocks.WOOL_STAIRS_CYAN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_DARK_GREEN = BLOCKS.register("wool_stairs_dark_green",
            () -> ModBlocks.WOOL_STAIRS_DARK_GREEN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_DARK_GREY = BLOCKS.register("wool_stairs_dark_grey",
            () -> ModBlocks.WOOL_STAIRS_DARK_GREY_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_GREEN = BLOCKS.register("wool_stairs_green",
            () -> ModBlocks.WOOL_STAIRS_GREEN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_LIGHT_BLUE = BLOCKS.register("wool_stairs_light_blue",
            () -> ModBlocks.WOOL_STAIRS_LIGHT_BLUE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_MAGENTA = BLOCKS.register("wool_stairs_magenta",
            () -> ModBlocks.WOOL_STAIRS_MAGENTA_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_ORANGE = BLOCKS.register("wool_stairs_orange",
            () -> ModBlocks.WOOL_STAIRS_ORANGE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_PURPLE = BLOCKS.register("wool_stairs_purple",
            () -> ModBlocks.WOOL_STAIRS_PURPLE_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_PINK = BLOCKS.register("wool_stairs_pink",
            () -> ModBlocks.WOOL_STAIRS_PINK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_RED = BLOCKS.register("wool_stairs_red",
            () -> ModBlocks.WOOL_STAIRS_RED_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_YELLOW = BLOCKS.register("wool_stairs_yellow",
            () -> ModBlocks.WOOL_STAIRS_YELLOW_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_LIGHT_GREY = BLOCKS.register("wool_stairs_light_grey",
            () -> ModBlocks.WOOL_STAIRS_LIGHT_GREY_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_BLACK = BLOCKS.register("wool_stairs_black",
            () -> ModBlocks.WOOL_STAIRS_BLACK_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> WOOL_STAIRS_BROWN = BLOCKS.register("wool_stairs_brown",
            () -> ModBlocks.WOOL_STAIRS_BROWN_PROPERTIES
    );

    public static final DeferredHolder<Block, Block> WALL_LANTERN = BLOCKS.register("wall_lantern",
            () -> ModBlocks.WALL_LANTERN_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> GLASS_DOOR = BLOCKS.register("glass_door",
            () -> ModBlocks.GLASS_DOOR_PROPERTIES
    );

    public static final DeferredHolder<Block, Block> CULTIVATION_POT = BLOCKS.register("cultivation_pot",
            () -> ModBlocks.cultivationPot(Blocks.AIR)
    );
    public static final DeferredHolder<Block, Block> CULTIVATED_CHERRY_SAPLING = BLOCKS.register("cultivated_cherry_sapling",
            () -> ModBlocks.cultivationPot(Blocks.CHERRY_SAPLING)
    );
    public static final DeferredHolder<Block, Block> CULTIVATED_OAK_SAPLING = BLOCKS.register("cultivated_oak_sapling",
            () -> ModBlocks.cultivationPot(Blocks.OAK_SAPLING)
    );

    public static final DeferredHolder<Block, Block> MELON_PARFAIT = BLOCKS.register("melon_parfait",
            () -> MELON_PARFAIT_PROPERTIES);

    public static final DeferredHolder<Block, Block> CHAMOMILE = BLOCKS.register("chamomile",
            () -> CHAMOMILE_PROPERTIES);
    public static final DeferredHolder<Block, Block> MINT = BLOCKS.register("mint",
            () -> MINT_PROPERTIES);


    public static final DeferredHolder<Block, Block> LOCACACA_CACTUS = BLOCKS.register("locacaca_cactus",
            () -> ModBlocks.LOCACACA_CACTUS_PROPERTIES
    );
    public static final DeferredHolder<Block, Block> LOCACACA_BLOCK = BLOCKS.register("locacaca_plant",
            () -> ModBlocks.LOCACACA_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> NEW_LOCACACA_BLOCK = BLOCKS.register("new_locacaca_plant",
            () -> ModBlocks.NEW_LOCACACA_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> CULTIVATED_LOCACACA = BLOCKS.register("cultivated_locacaca",
            () -> ModBlocks.cultivationPot(NEW_LOCACACA_BLOCK.get()));

    public static final DeferredHolder<Block, Block> GASOLINE_SPLATTER = BLOCKS.register("gasoline_splatter",
            () -> ModBlocks.GASOLINE_SPLATTER_PROPERTIES);
    public static final DeferredHolder<Block, Block> BLOOD_SPLATTER = BLOCKS.register("blood_splatter",
            () -> ModBlocks.BLOOD_SPLATTER_PROPERTIES);
    public static final DeferredHolder<Block, Block> VIRUS_SPIT_SPLATTER = BLOCKS.register("virus_spit_splatter",
            () -> ModBlocks.VIRUS_SPIT_SPLATTER_PROPERTIES);
    public static final DeferredHolder<Block, Block> ACID_PUDDLE = BLOCKS.register("acid_puddle",
            () -> ModBlocks.ACID_PUDDLE_PROPERTIES);
    public static final DeferredHolder<Block, Block> POISON_TRAIL_MUSHROOM = BLOCKS.register("poison_trail_mushroom",
            () -> ModBlocks.POISON_TRAIL_MUSHROOM_PROPERTIES);
    public static final DeferredHolder<Block, Block> BLUE_BLOOD_SPLATTER = BLOCKS.register("blue_blood_splatter",
            () -> ModBlocks.BLUE_BLOOD_SPLATTER_PROPERTIES);
    public static final DeferredHolder<Block, Block> ENDER_BLOOD_SPLATTER = BLOCKS.register("ender_blood_splatter",
            () -> ModBlocks.ENDER_BLOOD_SPLATTER_PROPERTIES);
    public static final DeferredHolder<Block, Block> WIRE_TRAP = BLOCKS.register("wire_trap",
            () -> ModBlocks.WIRE_TRAP_PROPERTIES);
    public static final DeferredHolder<Block, Block> BARBED_WIRE = BLOCKS.register("barbed_wire",
            () -> ModBlocks.BARBED_WIRE_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> BARBED_WIRE_BUNDLE = BLOCKS.register("barbed_wire_bundle",
            () -> ModBlocks.BARBED_WIRE_BUNDLE_PROPERTIES);
    public static final DeferredHolder<Block, Block> GODDESS_STATUE_BLOCK = BLOCKS.register("goddess_statue",
            () -> ModBlocks.GODDESS_STATUE_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> MAGICIANS_STATUE_BLOCK = BLOCKS.register("magicians_statue",
            () -> MAGICIANS_STATUE_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> STREET_SIGN_DIO = BLOCKS.register("street_sign_dio",
            ModBlocks::getStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> STREET_SIGN_RIGHT = BLOCKS.register("street_sign_right",
            ModBlocks::getStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> STREET_SIGN_STOP = BLOCKS.register("street_sign_stop",
            ModBlocks::getStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> STREET_SIGN_YIELD = BLOCKS.register("street_sign_yield",
            ModBlocks::getStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> STREET_SIGN_DANGER = BLOCKS.register("street_sign_danger",
            ModBlocks::getStreetSignBlockProperties);

    public static final DeferredHolder<Block, Block> WALL_STREET_SIGN_DIO = BLOCKS.register("wall_street_sign_dio",
            ModBlocks::getWallStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> WALL_STREET_SIGN_RIGHT = BLOCKS.register("wall_street_sign_right",
            ModBlocks::getWallStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> WALL_STREET_SIGN_STOP = BLOCKS.register("wall_street_sign_stop",
            ModBlocks::getWallStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> WALL_STREET_SIGN_YIELD = BLOCKS.register("wall_street_sign_yield",
            ModBlocks::getWallStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> WALL_STREET_SIGN_DANGER = BLOCKS.register("wall_street_sign_danger",
            ModBlocks::getWallStreetSignBlockProperties);
    public static final DeferredHolder<Block, Block> CEILING_LIGHT = BLOCKS.register("ceiling_light",
            () -> ModBlocks.CEILING_LIGHT_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> MIRROR = BLOCKS.register("mirror",
            ModBlocks::getMirrorBlockProperties);
    public static final DeferredHolder<Block, Block> MINING_ALERT_BLOCK = BLOCKS.register("mining_alert_block",
            () -> ModBlocks.MINING_ALERT_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> FLESH_BLOCK = BLOCKS.register("flesh_block",
            () -> ModBlocks.FLESH_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> FOG_DIRT_COATING = BLOCKS.register("fog_dirt_coating",
            ModBlocks::getFogCoatingBlock);
    public static final DeferredHolder<Block, Block> FOG_DIRT = BLOCKS.register("fog_dirt",
            ModBlocks::getFogBlock);
    public static final DeferredHolder<Block, Block> FOG_TRAP = BLOCKS.register("fog_trap",ModBlocks::getFogTrapBlock);
    public static final DeferredHolder<Block, Block> STEREO = BLOCKS.register("stereo",
            () -> ModBlocks.STEREO_PROPERTIES);
    public static final DeferredHolder<Block, Block> HAND_BLOCK = BLOCKS.register("hand_block",
            () -> ModBlocks.HAND_PROPERTIES);
    public static final DeferredHolder<Block, Block> STAND_FIRE = BLOCKS.register("stand_fire",
            () -> ModBlocks.STAND_FIRE_PROPERTIES);
    public static final DeferredHolder<Block, Block> BUBBLE_SCAFFOLD = BLOCKS.register("bubble_scaffold",
            () -> ModBlocks.BUBBLE_SCAFFOLD_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> DIVER_LIMB = BLOCKS.register("diver_limb",
            () -> ModBlocks.DIVER_LIMB_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> SC_RAPIER_PLATFORM = BLOCKS.register("sc_rapier_platform",
            () -> ModBlocks.SC_RAPIER_PLATFORM_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> GE_BUSH = BLOCKS.register("ge_bush",
            () -> ModBlocks.GE_BUSH_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> GE_VINE = BLOCKS.register("ge_vine",
            () -> ModBlocks.GE_VINE_PROPERTIES);
    public static final DeferredHolder<Block, Block> INVISIBLOCK = BLOCKS.register("invisible_block",
            () -> ModBlocks.INVISIBLE_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> D4C_PORTAL = BLOCKS.register("d4c_portal",
            () -> ModBlocks.D4C_PORTAL_PROPERTIES);
    public static final DeferredHolder<Block, Block> OASIS_MUD_BLOCK = BLOCKS.register("oasis_mud_block",
            () -> ModBlocks.OASIS_MUD_BLOCK_PROPERTIES);
    public static final DeferredHolder<Block, Block> ORANGE_FIRE = BLOCKS.register("colored_fire_orange",
            () -> ModBlocks.ORANGE_FIRE_PROPERTIES);
    public static final DeferredHolder<Block, Block> BLUE_FIRE = BLOCKS.register("colored_fire_blue",
            () -> ModBlocks.BLUE_FIRE_PROPERTIES);
    public static final DeferredHolder<Block, Block> PURPLE_FIRE = BLOCKS.register("colored_fire_purple",
            () -> ModBlocks.PURPLE_FIRE_PROPERTIES);
    public static final DeferredHolder<Block, Block> GREEN_FIRE = BLOCKS.register("colored_fire_green",
            () -> ModBlocks.GREEN_FIRE_PROPERTIES);
    public static final DeferredHolder<Block, Block> DREAD_FIRE = BLOCKS.register("colored_fire_dread",
            () -> ModBlocks.DREAD_FIRE_PROPERTIES);
    public static final DeferredHolder<Block, Block> CREAM_FIRE = BLOCKS.register("colored_fire_cream",
            () -> ModBlocks.CREAM_FIRE_PROPERTIES);
    public static final DeferredHolder<Block, Block> FANCY_LIGHTER_BLOCK = BLOCKS.register("fancy_lighter_block",
            () -> ModBlocks.FANCY_LIGHTER_PROPRETIES);
    public static final DeferredHolder<Block, Block> GAMBLING_TABLE = BLOCKS.register("gambling_table",
            () -> ModBlocks.GAMBLING_TABLE_PROPERTIES);
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<GamblingTableBlockEntity>> GAMBLING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("gambling_table", () -> BlockEntityType.Builder.of(GamblingTableBlockEntity::new, GAMBLING_TABLE.get()).build(null));

    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<OasisMudBlockEntity>> OASIS_MUD_BLOCK_ENTITY = BLOCK_ENTITIES.register("oasis_mud_block",
            () -> BlockEntityType.Builder.of(OasisMudBlockEntity::new, OASIS_MUD_BLOCK.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "oasis_mud_block")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<MirrorBlockEntity>> MIRROR_BLOCK_ENTITY = BLOCK_ENTITIES.register("mirror",
            () -> BlockEntityType.Builder.of(MirrorBlockEntity::new, MIRROR.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "mirror")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<StereoBlockEntity>> STEREO_BLOCK_ENTITY = BLOCK_ENTITIES.register("stereo",
            () -> BlockEntityType.Builder.of(StereoBlockEntity::new, STEREO.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "stereo")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<HandBlockEntity>> HAND_BLOCK_ENTITY = BLOCK_ENTITIES.register("hand_block",
            () -> BlockEntityType.Builder.of(HandBlockEntity::new, HAND_BLOCK.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "hand_block")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<StandFireBlockEntity>> STAND_FIRE_BLOCK_ENTITY = BLOCK_ENTITIES.register("stand_fire",
            () -> BlockEntityType.Builder.of(StandFireBlockEntity::new, STAND_FIRE.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "stand_fire")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<BubbleScaffoldBlockEntity>> BUBBLE_SCAFFOLD_BLOCK_ENTITY = BLOCK_ENTITIES.register("block_scaffold",
            () -> BlockEntityType.Builder.of(BubbleScaffoldBlockEntity::new, BUBBLE_SCAFFOLD.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "block_scaffold")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<DiverLimbBlockEntity>> DIVER_LIMB_BLOCK_ENTITY = BLOCK_ENTITIES.register("diver_limb",
            () -> BlockEntityType.Builder.of(DiverLimbBlockEntity::new, DIVER_LIMB.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "diver_limb")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<SilverChariotRapierPlatformBlockEntity>> SC_RAPIER_PLATFORM_BLOCK_ENTITY = BLOCK_ENTITIES.register("sc_rapier_platform",
            () -> BlockEntityType.Builder.of(SilverChariotRapierPlatformBlockEntity::new, SC_RAPIER_PLATFORM.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "sc_rapier_platform")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<GEBushBlockEntity>> GE_BUSH_BLOCK_ENTITY = BLOCK_ENTITIES.register("ge_bush",
            () -> BlockEntityType.Builder.of(GEBushBlockEntity::new, GE_BUSH.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "ge_bush")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<InvisiBlockEntity>> INVISIBLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("invisible_block",
            () -> BlockEntityType.Builder.of(InvisiBlockEntity::new, INVISIBLOCK.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "invisible_block")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<D4CPortalBlockEntity>> D4C_PORTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("d4c_portal",
            () -> BlockEntityType.Builder.of(D4CPortalBlockEntity::new, D4C_PORTAL.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "d4c_portal")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<FogTrapBlockEntity>> FOG_TRAP_BLOCK_ENTITY = BLOCK_ENTITIES.register("fog_trap",
            () -> BlockEntityType.Builder.of(FogTrapBlockEntity::new, FOG_TRAP.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "fog_trap")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<CoffinBlockEntity>> COFFIN_BLOCK_ENTITY = BLOCK_ENTITIES.register("coffin_block",
            () -> BlockEntityType.Builder.of(CoffinBlockEntity::new, COFFIN_BLOCK.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "coffin_block")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<ChessPieceBlockEntity>> CHESS_PIECE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("chess_piece",
                    () -> BlockEntityType.Builder.of(ChessPieceBlockEntity::new, CHESS_PIECE.get()).
                            build(Util.fetchChoiceType(References.BLOCK_ENTITY, "chess_piece")));



    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<KingBedBlockEntity>> KING_BED_BLOCK_ENTITY = BLOCK_ENTITIES.register("king_bed_block",
            () -> BlockEntityType.Builder.of(KingBedBlockEntity::new, KING_BED_BLOCK.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "king_bed_block")));
    public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<FancyLighterBlockEntity>> FANCY_LIGHTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("fancy_lighter_block",
            () -> BlockEntityType.Builder.of(FancyLighterBlockEntity::new, FANCY_LIGHTER_BLOCK.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "fancy_lighter_block")));
    //public static final DeferredHolder<net.minecraft.world.level.block.entity.BlockEntityType<?>, BlockEntityType<ChessBoardBlockEntity>> CHESSBOARD_BLOCK_ENTITY = BLOCK_ENTITIES.register("chessboard_block",
    //        () -> BlockEntityType.Builder.of(ChessBoardBlockEntity::new, CHESSBOARD_BLOCK.get()).build(Util.fetchChoiceType(References.BLOCK_ENTITY, "chessboard_block")));

    private static boolean generatedFogBlocks;
    private static boolean generatedFogItems;
    private static final List<PendingFogItem> pendingFogItems = new ArrayList<>();

    private record PendingFogItem(ResourceLocation id, Block block, Block original, boolean coating) {}

    public static Map<ResourceLocation, Block> fogCoatingBlocks = new LinkedHashMap<>();
    public static Map<ResourceLocation, Block> fogBlocks = new LinkedHashMap<>();

    @SubscribeEvent
    public static void registerDynamicFogBlocks(RegisterEvent event) {
        event.register(Registries.BLOCK, helper -> {
            if (generatedFogBlocks) return;

            // Registering new blocks must not change the collection being traversed.
            for (Block original : BuiltInRegistries.BLOCK.stream().toList()) {
                ResourceLocation sourceId = BuiltInRegistries.BLOCK.getKey(original);
                if (!sourceId.getNamespace().equals("minecraft")) continue;
                ResourceLocation fogId = ResourceLocation.fromNamespaceAndPath(
                        Roundabout.MOD_ID, "fog_" + sourceId.getPath());
                if (BuiltInRegistries.BLOCK.containsKey(fogId)
                        || blockBlacklist.contains(sourceId.getPath()) || dontGen.contains(fogId.getPath())) {
                    dontGenState.add(fogId.getPath());
                    continue;
                }

                boolean rightSize = false;
                try {
                    VoxelShape shape = original.defaultBlockState().getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
                    rightSize = !shape.isEmpty() && shape.bounds().getYsize() == 1.0;
                } catch (Exception e) {
                    try {
                        rightSize = original.defaultBlockState().isCollisionShapeFullBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
                    } catch (Exception ex) {
                        Roundabout.LOGGER.debug("Couldn't get the shape of " + sourceId.getPath());
                    }
                }
                if (!original.defaultBlockState().getProperties().isEmpty() || !rightSize) continue;

                Block fog = getFogBlock();
                helper.register(fogId, fog);
                fogBlocks.put(fogId, fog);

                ResourceLocation coatingId = ResourceLocation.fromNamespaceAndPath(
                        Roundabout.MOD_ID, fogId.getPath() + "_coating");
                if (!blockBlacklist.contains(coatingId.getPath())) {
                    Block coating = getFogCoatingBlock();
                    helper.register(coatingId, coating);
                    fogCoatingBlocks.put(coatingId, coating);
                    pendingFogItems.add(new PendingFogItem(coatingId, coating, original, true));
                }
                // Preserve the creative tab's coating-before-block order.
                pendingFogItems.add(new PendingFogItem(fogId, fog, original, false));
            }
            generatedFogBlocks = true;
        });

        event.register(Registries.ITEM, helper -> {
            if (generatedFogItems) return;
            // NeoForge dispatches the block registry before the item registry.
            for (PendingFogItem pending : pendingFogItems) {
                Item item = pending.coating()
                        ? new FogCoatBlockItem(pending.block(), new Item.Properties(), pending.original())
                        : new FogBlockItem(pending.block(), new Item.Properties(), pending.original());
                helper.register(pending.id(), item);
                gennedFogItems.add(item);
                FOG_TAB_ITEMS.add(() -> item);
            }
            pendingFogItems.clear();
            generatedFogItems = true;
        });
    }
}
