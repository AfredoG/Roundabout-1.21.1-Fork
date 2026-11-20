package net.hydra.jojomod.registry;

import com.mojang.serialization.MapCodec;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.block.*;
import net.hydra.jojomod.block.handBlock.HandBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Block definition codecs; world block states still use the existing block registry IDs. */
public final class ForgeBlockTypes {
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES =
            DeferredRegister.create(Registries.BLOCK_TYPE, Roundabout.MOD_ID);

    static {
        BLOCK_TYPES.register("bloody_stone_mask", () -> BloodyStoneMaskBlock.CODEC);
        BLOCK_TYPES.register("bubble_scaffold", () -> BubbleScaffoldBlock.CODEC);
        BLOCK_TYPES.register("chamomile_plant", () -> ChamomilePlantBlock.CODEC);
        BLOCK_TYPES.register("chess_piece", () -> ChessPieceBlock.CODEC);
        BLOCK_TYPES.register("climbing_wire", () -> ClimbingWireBlock.CODEC);
        BLOCK_TYPES.register("d4c_portal", () -> D4CPortalBlock.CODEC);
        BLOCK_TYPES.register("diver_limb", () -> DiverLimbBlock.CODEC);
        BLOCK_TYPES.register("equippable_stone_mask", () -> EquippableStoneMaskBlock.CODEC);
        BLOCK_TYPES.register("fancy_lighter", () -> FancyLighterBlock.CODEC);
        BLOCK_TYPES.register("ge_bush", () -> GEBushBlock.CODEC);
        BLOCK_TYPES.register("gambling_table", () -> GamblingTableBlock.CODEC);
        BLOCK_TYPES.register("goddess_statue", () -> GoddessStatueBlock.CODEC);
        BLOCK_TYPES.register("hallucinatory_acid", () -> HallucinatoryAcidBlock.CODEC);
        BLOCK_TYPES.register("hallucinatory_acid_wall", () -> HallucinatoryAcidWallBlock.CODEC);
        BLOCK_TYPES.register("invisi", () -> InvisiBlock.CODEC);
        BLOCK_TYPES.register("locacaca", () -> LocacacaBlock.CODEC);
        BLOCK_TYPES.register("magicians_statue", () -> MagiciansStatueBlock.CODEC);
        BLOCK_TYPES.register("manor_table", () -> ManorTableBlock.CODEC);
        BLOCK_TYPES.register("mint_plant", () -> MintPlantBlock.CODEC);
        BLOCK_TYPES.register("mirror", () -> MirrorBlock.CODEC);
        BLOCK_TYPES.register("new_locacaca", () -> NewLocacacaBlock.CODEC);
        BLOCK_TYPES.register("oasis_mud", () -> OasisMudBlock.CODEC);
        BLOCK_TYPES.register("plant", () -> PlantBlock.CODEC);
        BLOCK_TYPES.register("poison_trail_mushroom", () -> PoisonTrailMushroomBlock.CODEC);
        BLOCK_TYPES.register("silver_chariot_rapier_platform", () -> SilverChariotRapierPlatformBlock.CODEC);
        BLOCK_TYPES.register("stand_fire", () -> StandFireBlock.CODEC);
        BLOCK_TYPES.register("stone_mask", () -> StoneMaskBlock.CODEC);
        BLOCK_TYPES.register("street_sign", () -> StreetSignBlock.CODEC);
        BLOCK_TYPES.register("street_wall_sign", () -> StreetWallSignBlock.CODEC);
        BLOCK_TYPES.register("hand", () -> HandBlock.CODEC);
        BLOCK_TYPES.register("stereo", () -> StereoBlock.STEREO_CODEC);
    }

    private ForgeBlockTypes() {}
}
