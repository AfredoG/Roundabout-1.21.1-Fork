package net.hydra.jojomod.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public final class ClientboundBlackSabbathOpenPacket implements CustomPacketPayload {
    public static final Type<ClientboundBlackSabbathOpenPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("roundabout", "black_sabbath_open"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundBlackSabbathOpenPacket> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public ClientboundBlackSabbathOpenPacket decode(RegistryFriendlyByteBuf buffer) {
                    return new ClientboundBlackSabbathOpenPacket(buffer.readUnsignedByte(), buffer.readInt());
                }
                @Override
                public void encode(RegistryFriendlyByteBuf buffer, ClientboundBlackSabbathOpenPacket packet) {
                    buffer.writeByte(packet.containerId);
                    buffer.writeInt(packet.entityId);
                }
            };
    protected final int containerId;
    protected final int entityId;

    public ClientboundBlackSabbathOpenPacket(int containerId, int entityId) {
        this.containerId = containerId;
        this.entityId = entityId;
    }

    @Override
    public Type<ClientboundBlackSabbathOpenPacket> type() { return TYPE; }
}
