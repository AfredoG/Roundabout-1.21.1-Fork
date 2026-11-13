package net.zetalasis.networking.packet.impl;

import io.netty.buffer.Unpooled;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.client.ClientUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.zetalasis.networking.packet.api.IClientNetworking;
import net.zetalasis.networking.packet.api.args.c2s.AbstractBaseC2SPacket;
import net.zetalasis.networking.packet.api.args.s2c.AbstractBaseS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.zetalasis.networking.packet.impl.packet.MessageC2S;
import net.zetalasis.networking.packet.impl.packet.MessageS2C;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModNetworking {
    public static final Map<Class<?>, BiConsumer<FriendlyByteBuf, Object>> ENCODERS = MessageArguments.ENCODERS;
    private static final HashMap<ResourceLocation, AbstractBaseC2SPacket> registrarC2S = new HashMap<>();
    private static final HashMap<ResourceLocation, AbstractBaseS2CPacket> registrarS2C = new HashMap<>();

    public static void bootstrap() {
        register(new MessageC2S());
        register(new MessageS2C());
    }

    public static @Nullable Connection getC2SConnection()
    {
        return ClientUtil.getC2SConnection();
    }

    private static ResourceLocation buildFromClassName(Class<?> c)
    {
        return (Roundabout.location(c.getSimpleName().toLowerCase()));
    }

    public static <T extends AbstractBaseC2SPacket> void register(T packet) {
        if (registrarC2S.containsValue(packet))
        {
            Roundabout.LOGGER.warn("Duplicate caught while registering C2S packet \"{}\"", buildFromClassName(packet.getClass()));
            return;
        }

        registrarC2S.put(buildFromClassName(packet.getClass()), packet);
    }

    public static <T extends AbstractBaseS2CPacket> void register(T packet) {
        if (registrarS2C.containsValue(packet)) {
            Roundabout.LOGGER.warn("Duplicate caught while registering S2C packet \"{}\"", buildFromClassName(packet.getClass()));
            return;
        }

        registrarS2C.put(buildFromClassName(packet.getClass()), packet);
    }

    public static <T extends AbstractBaseC2SPacket> void send(T packetType, Object... args) {
        Connection con = getC2SConnection();
        if (con == null)
            return;

        con.send(new ServerboundCustomPayloadPacket(new MessagePayload(buildFromClassName(packetType.getClass()), args)));
    }

    public static <T extends AbstractBaseS2CPacket> void send(T packetType, ServerPlayer recipient, Object... args) {
        if (recipient != null && recipient.connection != null) {
            recipient.connection.send(new ClientboundCustomPayloadPacket(
                    new MessagePayload(buildFromClassName(packetType.getClass()), args)));
        }
    }

    public static Object[] decodeBufferToVArgs(FriendlyByteBuf buffer) {
        return MessageArguments.decodeBufferToVArgs(buffer);
    }
    public static void encodeValue(FriendlyByteBuf buffer, Object value) {
        MessageArguments.encodeValue(buffer, value);
    }
    public static Object decodeValue(FriendlyByteBuf buffer, Class<?> type) {
        return MessageArguments.decodeValue(buffer, type);
    }
    public static Set<ResourceLocation> clientboundIds() { return Set.copyOf(registrarS2C.keySet()); }
    public static Set<ResourceLocation> serverboundIds() { return Set.copyOf(registrarC2S.keySet()); }

    /** Attempts to find the C2S packet associated with the ResourceLocation ID
     * @return The packet if found. Otherwise, null. */
    public static @Nullable AbstractBaseC2SPacket getC2S(ResourceLocation location)
    {
        return registrarC2S.get(location);
    }

    /** Attempts to find the S2C packet associated with the ResourceLocation ID
     * @return The packet if found. Otherwise, null. */
    public static @Nullable AbstractBaseS2CPacket getS2C(ResourceLocation location)
    {
        return registrarS2C.get(location);
    }
}