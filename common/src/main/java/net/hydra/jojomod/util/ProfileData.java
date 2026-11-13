package net.hydra.jojomod.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

/** Legacy profile NBT for Roundabout's saved hands; keeps existing worlds readable. */
public final class ProfileData {
    private ProfileData() {}

    public static CompoundTag write(GameProfile profile) {
        CompoundTag tag = new CompoundTag();
        if (profile.getId() != null) tag.putUUID("Id", profile.getId());
        if (profile.getName() != null) tag.putString("Name", profile.getName());
        CompoundTag properties = new CompoundTag();
        profile.getProperties().asMap().forEach((name, values) -> {
            ListTag list = new ListTag();
            for (Property property : values) {
                CompoundTag value = new CompoundTag();
                value.putString("Value", property.value());
                if (property.signature() != null) value.putString("Signature", property.signature());
                list.add(value);
            }
            properties.put(name, list);
        });
        if (!properties.isEmpty()) tag.put("Properties", properties);
        return tag;
    }

    @Nullable
    public static GameProfile read(CompoundTag tag) {
        UUID id = tag.hasUUID("Id") ? tag.getUUID("Id") : null;
        String name = tag.contains("Name", Tag.TAG_STRING) ? tag.getString("Name") : null;
        if (id == null && (name == null || name.isEmpty())) return null;
        // Authlib 6 requires both fields. Preserve name-only legacy tags with an offline UUID.
        if (id == null) id = net.minecraft.core.UUIDUtil.createOfflinePlayerUUID(name);
        GameProfile profile = new GameProfile(id, name == null ? "" : name);
        CompoundTag properties = tag.getCompound("Properties");
        for (String key : properties.getAllKeys()) {
            ListTag values = properties.getList(key, Tag.TAG_COMPOUND);
            for (int i = 0; i < values.size(); i++) {
                CompoundTag value = values.getCompound(i);
                profile.getProperties().put(key, value.contains("Signature", Tag.TAG_STRING)
                        ? new Property(key, value.getString("Value"), value.getString("Signature"))
                        : new Property(key, value.getString("Value")));
            }
        }
        return profile;
    }
}
