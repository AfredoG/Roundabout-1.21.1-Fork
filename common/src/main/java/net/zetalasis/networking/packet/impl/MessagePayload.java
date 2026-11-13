package net.zetalasis.networking.packet.impl;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Keeps upstream message arguments while using the 1.21 payload transport. */
public record MessagePayload(ResourceLocation id, Object[] arguments) implements CustomPacketPayload {
    @Override public Type<MessagePayload> type() { return new Type<>(id); }
    public static StreamCodec<RegistryFriendlyByteBuf, MessagePayload> codec(ResourceLocation id) {
        return new StreamCodec<>() {
            @Override public MessagePayload decode(RegistryFriendlyByteBuf buffer) {
                return new MessagePayload(id, MessageArguments.decodeBufferToVArgs(buffer));
            }
            @Override public void encode(RegistryFriendlyByteBuf buffer, MessagePayload payload) {
                MessageArguments.encodeArguments(buffer, payload.arguments());
            }
        };
    }
}
