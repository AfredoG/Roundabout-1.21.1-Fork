package net.hydra.jojomod.networking;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.zetalasis.networking.packet.api.args.s2c.PacketArgsS2C;
import net.zetalasis.networking.packet.impl.MessagePayload;
import net.zetalasis.networking.packet.impl.ModNetworking;

/** Kept separate so dedicated-server registration does not initialize client classes. */
final class NeoForgeClientMessages {
    private NeoForgeClientMessages() {}
    static void handle(MessagePayload payload) {
        var minecraft = Minecraft.getInstance();
        var connection = minecraft.getConnection();
        var handler = ModNetworking.getS2C(payload.id());
        if (connection == null || handler == null) return;
        var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(0), connection.registryAccess());
        try {
            handler.handle(new PacketArgsS2C(minecraft, connection, payload.arguments()), buffer);
        } finally {
            buffer.release();
        }
    }
}
