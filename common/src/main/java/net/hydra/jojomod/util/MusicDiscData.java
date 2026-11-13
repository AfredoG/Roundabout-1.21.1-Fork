package net.hydra.jojomod.util;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;

/** Shared access to both vanilla and data-pack jukebox songs. */
public final class MusicDiscData {
    private MusicDiscData() {}

    public static ResourceKey<JukeboxSong> key(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath("roundabout", name));
    }

    public static boolean isDisc(ItemStack stack) {
        return stack.has(DataComponents.JUKEBOX_PLAYABLE);
    }

    public static Optional<Holder<JukeboxSong>> song(ItemStack stack, HolderLookup.Provider registries) {
        return JukeboxSong.fromStack(registries, stack);
    }
}
