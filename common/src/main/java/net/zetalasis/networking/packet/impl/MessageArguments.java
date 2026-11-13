package net.zetalasis.networking.packet.impl;

import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class MessageArguments {
    private static final HashMap<Class<?>, Function<FriendlyByteBuf, Object>> DECODERS = new HashMap<>();
    public static final Map<Class<?>, BiConsumer<FriendlyByteBuf, Object>> ENCODERS = new HashMap<>();
    static {
        DECODERS.put(Character.class, buf -> buf.readChar());
        DECODERS.put(Integer.class, FriendlyByteBuf::readInt);
        DECODERS.put(Long.class, FriendlyByteBuf::readLong);
        DECODERS.put(Float.class, FriendlyByteBuf::readFloat);
        DECODERS.put(Double.class, FriendlyByteBuf::readDouble);
        DECODERS.put(Boolean.class, FriendlyByteBuf::readBoolean);
        DECODERS.put(Byte.class, FriendlyByteBuf::readByte);
        DECODERS.put(Short.class, FriendlyByteBuf::readShort);
        DECODERS.put(String.class, FriendlyByteBuf::readUtf);
        DECODERS.put(UUID.class, buf -> buf.readUUID());
        DECODERS.put(ResourceLocation.class, FriendlyByteBuf::readResourceLocation);
        DECODERS.put(BlockPos.MutableBlockPos.class, buf -> buf.readBlockPos().mutable());
        DECODERS.put(BlockPos.class, buf -> buf.readBlockPos());
        DECODERS.put(Vector3f.class, buf -> buf.readVector3f());
        DECODERS.put(ItemStack.class, buf -> ItemStack.OPTIONAL_STREAM_CODEC.decode((net.minecraft.network.RegistryFriendlyByteBuf) buf));
        DECODERS.put(BlockHitResult.class, FriendlyByteBuf::readBlockHitResult);
        DECODERS.put(CompoundTag.class, buf -> buf.readNbt());

        ENCODERS.put(Integer.class, (buf, o) -> buf.writeInt((Integer) o));
        ENCODERS.put(int.class, (buf, o) -> buf.writeInt((Integer) o));
        ENCODERS.put(Long.class, (buf, o) -> buf.writeLong((Long) o));
        ENCODERS.put(long.class, (buf, o) -> buf.writeLong((Long) o));
        ENCODERS.put(Float.class, (buf, o) -> buf.writeFloat((Float) o));
        ENCODERS.put(float.class, (buf, o) -> buf.writeFloat((Float) o));
        ENCODERS.put(Double.class, (buf, o) -> buf.writeDouble((Double) o));
        ENCODERS.put(double.class, (buf, o) -> buf.writeDouble((Double) o));
        ENCODERS.put(Boolean.class, (buf, o) -> buf.writeBoolean((Boolean) o));
        ENCODERS.put(boolean.class, (buf, o) -> buf.writeBoolean((Boolean) o));
        ENCODERS.put(Byte.class, (buf, o) -> buf.writeByte((Byte) o));
        ENCODERS.put(byte.class, (buf, o) -> buf.writeByte((Byte) o));
        ENCODERS.put(Short.class, (buf, o) -> buf.writeShort((Short) o));
        ENCODERS.put(short.class, (buf, o) -> buf.writeShort((Short) o));
        ENCODERS.put(Character.class, (buf, o) -> buf.writeChar((Character) o));
        ENCODERS.put(char.class, (buf, o) -> buf.writeChar((Character) o));
        ENCODERS.put(String.class, (buf, o) -> buf.writeUtf((String) o));
        ENCODERS.put(UUID.class, (buf, o) -> buf.writeUUID((UUID) o));
        ENCODERS.put(ResourceLocation.class, (buf, o) -> buf.writeResourceLocation((ResourceLocation) o));
        ENCODERS.put(BlockPos.MutableBlockPos.class, (buf, o) -> buf.writeBlockPos((BlockPos) o));
        ENCODERS.put(BlockPos.class, (buf, o) -> buf.writeBlockPos((BlockPos) o));
        ENCODERS.put(ItemStack.class, (buf, o) -> ItemStack.OPTIONAL_STREAM_CODEC.encode((net.minecraft.network.RegistryFriendlyByteBuf) buf, (ItemStack) o));
        ENCODERS.put(Vector3f.class, (buf, o) -> buf.writeVector3f((Vector3f)o));
        ENCODERS.put(BlockHitResult.class, (buf, o) -> buf.writeBlockHitResult((BlockHitResult) o));
        ENCODERS.put(CompoundTag.class, (buf, o) -> buf.writeNbt((CompoundTag) o));
    }

    public static void encodeValue(FriendlyByteBuf buf, Object value) {
        BiConsumer<FriendlyByteBuf, Object> encoder = ENCODERS.get(value.getClass());
        if (encoder == null) {
            throw new IllegalArgumentException("No encoder for type: " + value.getClass().getName());
        }
        encoder.accept(buf, value);
    }

    public static Object decodeValue(FriendlyByteBuf buf, Class<?> type) {
        Function<FriendlyByteBuf, Object> decoder = DECODERS.get(type);
        if (decoder == null) {
            throw new IllegalArgumentException("No decoder for type: " + type.getName());
        }
        return decoder.apply(buf);
    }

    private static String generateSignature(Object... args) {
        if (args.length == 0)
            return "void";  // special marker for no arguments

        StringBuilder sig = new StringBuilder();

        for (Object arg : args) {
            Class<?> c = arg.getClass();

            sig.append(c.getName()).append(";");
        }

        return sig.toString();
    }

    public static void encodeArguments(FriendlyByteBuf buf, Object... args) {

        // type metadata
        buf.writeUtf(generateSignature(args));

        // actual params
        for (Object arg : args) {
            encodeValue(buf, arg);
        }

        // final buffer looks like:
        // ["Ljava/lang/String;IJD", "Hello", 42, 10000000000L, 3.14]

    }

    public static Object[] decodeBufferToVArgs(FriendlyByteBuf buf) {
        String sig = buf.readUtf();

        if (sig.equals("void")) {
            return new Object[0]; // no arguments
        }

        List<Object> result = new ArrayList<>();
        String[] sigClasses = sig.split(";");

        for (String className : sigClasses) {
            if (className.isEmpty()) continue; // skip empty due to trailing ";"
            try {
                Class<?> clazz = DECODERS.keySet().stream().filter(c -> c.getName().equals(className)).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unsupported argument type: " + className));
                result.add(decodeValue(buf, clazz));
            } catch (Exception e) {
                throw new RuntimeException("Failed decoding FriendlyByteBuf to VArgs:\n" +
                        className + " was not found.\n[" + sig + "]");
            }
        }

        if (buf.readableBytes() > 0) {
            throw new RuntimeException("Warning: extra unread bytes in buffer! Signature: " + sig);
        }

        return result.toArray();
    }

}
