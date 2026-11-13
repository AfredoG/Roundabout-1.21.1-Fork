package net.hydra.jojomod.networking;

import io.netty.buffer.Unpooled;
import java.util.Set;
import net.hydra.jojomod.Roundabout;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.zetalasis.networking.packet.api.args.c2s.PacketArgsC2S;
import net.zetalasis.networking.packet.impl.MessagePayload;
import net.zetalasis.networking.packet.impl.ModNetworking;

/** Loader-specific transport; upstream message handlers keep their public API. */
public final class NeoForgeMessages {
    private NeoForgeMessages() {}
    private static final Set<String> NETWORK_MESSAGES = Set.of(
            "thread_hop_time_stop_hovering", "thread_hop_update_pilot", "thread_hop_moving_sync",
            "thread_hop_handshake", "thread_hop_d4c_request_dimension_hop");

    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        for (var id : ModNetworking.clientboundIds()) {
            registrar.playToClient(new CustomPacketPayload.Type<MessagePayload>(id), MessagePayload.codec(id),
                    (payload, context) -> NeoForgeClientMessages.handle(payload));
        }
        var serverRegistrar = registrar.executesOn(HandlerThread.NETWORK);
        for (var id : ModNetworking.serverboundIds()) {
            serverRegistrar.playToServer(new CustomPacketPayload.Type<MessagePayload>(id), MessagePayload.codec(id),
                    NeoForgeMessages::handleServer);
        }
    }

    private static void handleServer(MessagePayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) return;
        var args = payload.arguments();
        if (args.length == 0 || !(args[0] instanceof String message)) return;
        Runnable dispatch = () -> {
            var handler = ModNetworking.getC2S(payload.id());
            if (handler == null) return;
            // Legacy handlers receive a buffer already consumed by argument decoding.
            var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(0), player.registryAccess());
            try {
                handler.handle(new PacketArgsC2S(player.server, player, player.connection, args), buffer);
            } finally {
                buffer.release();
            }
        };
        if (NETWORK_MESSAGES.contains(message)) dispatch.run();
        else context.enqueueWork(dispatch).exceptionally(error -> {
            Roundabout.LOGGER.error("Failed to handle Roundabout message {}", message, error);
            context.disconnect(Component.literal("Failed to handle Roundabout message"));
            return null;
        });
    }
}
