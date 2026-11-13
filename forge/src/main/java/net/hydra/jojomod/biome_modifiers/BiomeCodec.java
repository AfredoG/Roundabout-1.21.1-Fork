package net.hydra.jojomod.biome_modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.hydra.jojomod.Roundabout;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BiomeCodec {
        public static DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
                DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Roundabout.MOD_ID);

        static DeferredHolder<com.mojang.serialization.MapCodec<? extends net.neoforged.neoforge.common.world.BiomeModifier>, MapCodec<naturalLocacacaModifier>> LOCA_CODEC = BIOME_MODIFIER_SERIALIZERS.register("locacaca_placement", () ->
                RecordCodecBuilder.mapCodec(builder -> builder.group(
                        // declare fields
                        Biome.LIST_CODEC.fieldOf("biomes").forGetter(naturalLocacacaModifier::biomes),
                        PlacedFeature.CODEC.fieldOf("feature").forGetter(naturalLocacacaModifier::feature)
                        // declare constructor
                ).apply(builder, naturalLocacacaModifier::new)));
}
