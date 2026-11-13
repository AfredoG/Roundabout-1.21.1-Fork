package net.hydra.jojomod.registry;

import net.hydra.jojomod.Roundabout;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Roundabout.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ClientForgeForgeEvents {

    @SubscribeEvent
    public static void clientTickEvent(ClientTickEvent.Post event) {
    }
}
