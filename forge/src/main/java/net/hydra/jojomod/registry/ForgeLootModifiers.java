package net.hydra.jojomod.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.Utils.ForgeItemModifiers;
import net.hydra.jojomod.Utils.ForgeSusGravelItemModifier;
import net.hydra.jojomod.Utils.ForgeSusSandItemModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ForgeLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Roundabout.MOD_ID);

    public static final DeferredHolder<com.mojang.serialization.MapCodec<? extends net.neoforged.neoforge.common.loot.IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> ADD_SUS_SAND_ITEM =
            LOOT_MODIFIERS.register("add_sus_sand_item", ForgeSusSandItemModifier.CODEC);
    public static final DeferredHolder<com.mojang.serialization.MapCodec<? extends net.neoforged.neoforge.common.loot.IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> ADD_SUS_GRAVEL_ITEM =
            LOOT_MODIFIERS.register("add_sus_gravel_item", ForgeSusGravelItemModifier.CODEC);
    public static final DeferredHolder<com.mojang.serialization.MapCodec<? extends net.neoforged.neoforge.common.loot.IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> ADD_ITEMS =
            LOOT_MODIFIERS.register("add_items", ForgeItemModifiers.CODEC);
}
