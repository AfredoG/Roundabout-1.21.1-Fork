package net.hydra.jojomod;

import net.neoforged.neoforge.event.server.ServerStartedEvent;

public class RoundaboutModForgeServer {

    public static void entityLifeCycle(ServerStartedEvent event) {
        RoundaboutLoadServer.onServerStarted(event.getServer());
    }
}
