package net.hydra.jojomod;

import net.hydra.jojomod.Utils.commands.ForgeCommandRegistry;
import net.hydra.jojomod.biome_modifiers.BiomeCodec;
import net.hydra.jojomod.client.ClientClass;
import net.hydra.jojomod.networking.ForgeNetworkHandler;
import net.hydra.jojomod.networking.ForgeJJFNetwork;
import net.hydra.jojomod.registry.*;
import net.hydra.jojomod.util.config.ConfigManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLPaths;

import static net.hydra.jojomod.registry.ForgeBlocks.registerDynamicFogBlocks;

@Mod(Roundabout.MOD_ID)
public class RoundaboutModForge {
    public RoundaboutModForge(IEventBus bus) {
        bus.addListener(net.hydra.jojomod.networking.NeoForgeMessages::register);
        bus.addListener(ForgeNetworkHandler::registerPayloads);
        bus.addListener(ForgeJJFNetwork::registerPayloads);

        ConfigManager.loadConfigs(FMLPaths.CONFIGDIR.get().resolve(Roundabout.MOD_ID + ".json"),
                //FMLPaths.CONFIGDIR.get().resolve(Roundabout.MOD_ID + "-server.json"),
                FMLPaths.CONFIGDIR.get().resolve(Roundabout.MOD_ID + "-advanced.json"),
                FMLPaths.CONFIGDIR.get().resolve(Roundabout.MOD_ID + "-clientOnly.json"));
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForgeCommonConfig.SPEC, "roundabout-client.toml");
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeCommonConfig.SPEC, "roundabout-common.toml");

        /**
        BlockBlacklist.load(
                FMLPaths.CONFIGDIR.get(),
                "roundabout-block_blacklist"
        );
         **/

        ForgeEffects.POTION_EFFECTS.register(bus);
        ForgeEntities.ENTITY_TYPES.register(bus);
        ForgeSounds.SOUNDS.register(bus);
        ForgeBlocks.BLOCKS.register(bus);
        ForgeBlocks.BLOCK_ENTITIES.register(bus);
        ForgeItems.ITEMS.register(bus);

        ForgeItems.POTIONS.register(bus);
        ForgeCreativeTab.TABS.register(bus);
        ForgeParticles.PARTICLES.register(bus);
        ForgeMenus.MENUS.register(bus);
        ForgeGamerules.registerGamerules();

        //
        BiomeCodec.BIOME_MODIFIER_SERIALIZERS.register(bus);
        ForgeLootModifiers.LOOT_MODIFIERS.register(bus);

        bus.addListener(this::clientSetup);
        bus.addListener(this::commonSetup);
        bus.addListener(this::loadComplete);
        NeoForge.EVENT_BUS.addListener(this::entityLifeCycle);


        Roundabout.LOGGER.info("Hello Forge world!");
        Roundabout.init(true);

        //ForgeItems.assignStupidForge();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientClass.init();
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        NeoForge.EVENT_BUS.register(new ForgeCommandRegistry());
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            ForgeJJFNetwork.register();
            ForgeMenus.initialize();
        });
    }

    @EventBusSubscriber(modid = Roundabout.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLCommonSetupEvent event) {
            ForgeItemProperties.addCustomItemProperties();
        }
    }

    public void entityLifeCycle(ServerStartedEvent event) {
        RoundaboutModForgeServer.entityLifeCycle(event);
    }

}
