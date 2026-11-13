package net.hydra.jojomod.platform.services;

import net.hydra.jojomod.Roundabout;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean canControlMineBlock(ServerPlayer player, BlockPos pos, Direction direction,
                                       ServerboundPlayerActionPacket.Action action) {
        PlayerInteractEvent.LeftClickBlock event = CommonHooks.onLeftClickBlock(player, pos, direction, action);
        return !event.isCanceled() && (player.isCreative()
                || event.getUseBlock() != TriState.FALSE
                && event.getUseItem() != TriState.FALSE);
    }

    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean getBoss(LivingEntity LE) {
        return LE.getType().is(Tags.EntityTypes.BOSSES);
    }

    @Override
    public TagKey<Block> getOreTag() {
        return Tags.Blocks.ORES;
    }

    public ResourceLocation soundId(SoundEvent event){
        Roundabout.LOGGER.info("fgfg");
        return BuiltInRegistries.SOUND_EVENT.getKey(event);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @SuppressWarnings("removal")
    public boolean getFluidTagPushCode(Entity ent, TagKey<Fluid> $$0, double $$1){
        ent.updateFluidHeightAndDoFluidPushing();
        if($$0 == FluidTags.WATER) return ent.isInFluidType(net.neoforged.neoforge.common.NeoForgeMod.WATER_TYPE.value());
        else if ($$0 == FluidTags.LAVA) return ent.isInFluidType(net.neoforged.neoforge.common.NeoForgeMod.LAVA_TYPE.value());
        else return false;
    }

}
