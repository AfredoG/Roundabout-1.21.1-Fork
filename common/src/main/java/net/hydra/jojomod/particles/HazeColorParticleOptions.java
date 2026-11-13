package net.hydra.jojomod.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hydra.jojomod.event.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;

public class HazeColorParticleOptions implements ParticleOptions {
    private final ParticleType<HazeColorParticleOptions> type;
    private final float r, g, b;

    public HazeColorParticleOptions(ParticleType<HazeColorParticleOptions> type, float r, float g, float b) {
        this.type = type;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static HazeColorParticleOptions fromPackedColor(ParticleType<HazeColorParticleOptions> type, int color) {
        return new HazeColorParticleOptions(
                type,
                ((color >> 16) & 0xFF) / 255.0F,
                ((color >> 8) & 0xFF) / 255.0F,
                (color & 0xFF) / 255.0F
        );
    }

    public float getR() { return r; }
    public float getG() { return g; }
    public float getB() { return b; }

    public static com.mojang.serialization.MapCodec<HazeColorParticleOptions> codec(ParticleType<HazeColorParticleOptions> type) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.FLOAT.fieldOf("r").forGetter(o -> o.r),
                Codec.FLOAT.fieldOf("g").forGetter(o -> o.g),
                Codec.FLOAT.fieldOf("b").forGetter(o -> o.b)
        ).apply(instance, (r, g, b) -> new HazeColorParticleOptions(type, r, g, b)));
    }

    public static net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, HazeColorParticleOptions> streamCodec(ParticleType<HazeColorParticleOptions> type) {
        return new net.minecraft.network.codec.StreamCodec<>() {
            @Override public HazeColorParticleOptions decode(net.minecraft.network.RegistryFriendlyByteBuf buffer) {
                return new HazeColorParticleOptions(type, buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
            }
            @Override public void encode(net.minecraft.network.RegistryFriendlyByteBuf buffer, HazeColorParticleOptions value) {
                buffer.writeFloat(value.r);
                buffer.writeFloat(value.g);
                buffer.writeFloat(value.b);
            }
        };
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

}
