package net.hydra.jojomod.networking;

import net.hydra.jojomod.client.gui.BlackSabbathPlayerInventoryMenu;
import net.hydra.jojomod.client.gui.BlackSabbathPlayerInventoryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handleClientboundBlackSabbathOpenPacket(ClientboundBlackSabbathOpenPacket message, IPayloadContext context) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        ClientLevel level = player.clientLevel;
        Entity entity = level.getEntity(message.entityId);

        if (entity instanceof LocalPlayer pl) {
            BlackSabbathPlayerInventoryMenu blackSabbathMenu = new BlackSabbathPlayerInventoryMenu(pl.getInventory(), pl, message.containerId);
            player.containerMenu = blackSabbathMenu;
            Minecraft.getInstance().setScreen(new BlackSabbathPlayerInventoryScreen(blackSabbathMenu, pl.getInventory(), pl));
        }
    }
}
