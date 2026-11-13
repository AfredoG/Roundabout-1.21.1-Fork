package net.hydra.jojomod.networking;

import net.hydra.jojomod.event.powers.jumpingJackFlash.ZeroGravitySync;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ForgeJJFNetwork {
    private ForgeJJFNetwork() {}

    public record SnapshotPayload(ZeroGravitySync.Snapshot snapshot) implements CustomPacketPayload {
        public static final Type<SnapshotPayload> TYPE = new Type<>(
                ResourceLocation.fromNamespaceAndPath("roundabout", "zero_gravity_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, SnapshotPayload> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public SnapshotPayload decode(RegistryFriendlyByteBuf buffer) {
                return new SnapshotPayload(ZeroGravitySync.read(buffer));
            }
            @Override
            public void encode(RegistryFriendlyByteBuf buffer, SnapshotPayload payload) {
                ZeroGravitySync.write(buffer, payload.snapshot());
            }
        };
        @Override
        public Type<SnapshotPayload> type() { return TYPE; }
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(SnapshotPayload.TYPE, SnapshotPayload.STREAM_CODEC,
                (payload, context) -> ForgeJJFClientPackets.apply(payload.snapshot()));
    }

    public static void register() {
        ZeroGravitySync.registerSender((player, snapshot) ->
                PacketDistributor.sendToPlayer(player, new SnapshotPayload(snapshot)));
    }
}
