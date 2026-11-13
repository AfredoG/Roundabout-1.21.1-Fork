package net.hydra.jojomod.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ForgeNetworkHandler {
    private ForgeNetworkHandler() {}

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(ClientboundBlackSabbathOpenPacket.TYPE,
                ClientboundBlackSabbathOpenPacket.STREAM_CODEC,
                (packet, context) -> ClientPacketHandler.handleClientboundBlackSabbathOpenPacket(packet, context));
    }

    public static void sendToClient(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}
