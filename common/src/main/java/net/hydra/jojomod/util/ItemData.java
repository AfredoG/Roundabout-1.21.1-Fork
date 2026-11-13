package net.hydra.jojomod.util;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/** Copy-on-write access to Roundabout's custom item data.
 * Returned tags are detached snapshots: persist changes with set or update.
 * Vanilla item properties use their own components, never CUSTOM_DATA.
 */
public final class ItemData {
    private ItemData() {}
    @Nullable public static CompoundTag get(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data == null ? null : data.copyTag();
    }
    public static CompoundTag copy(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }
    public static boolean has(ItemStack stack) {
        return !stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).isEmpty();
    }
    @Nullable public static CompoundTag element(ItemStack stack, String key) {
        CompoundTag root = get(stack);
        return root != null && root.contains(key, Tag.TAG_COMPOUND) ? root.getCompound(key) : null;
    }
    public static CompoundTag elementOrEmpty(ItemStack stack, String key) {
        CompoundTag child = element(stack, key);
        return child == null ? new CompoundTag() : child;
    }
    public static void set(ItemStack stack, @Nullable CompoundTag value) {
        if (value == null || value.isEmpty()) stack.remove(DataComponents.CUSTOM_DATA);
        else CustomData.set(DataComponents.CUSTOM_DATA, stack, value);
    }
    public static void update(ItemStack stack, Consumer<CompoundTag> edit) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, edit);
    }
    public static void updateElement(ItemStack stack, String key, Consumer<CompoundTag> edit) {
        update(stack, root -> {
            CompoundTag child = root.getCompound(key);
            edit.accept(child);
            root.put(key, child);
        });
    }
    public static void setElement(ItemStack stack, String key, CompoundTag value) {
        update(stack, root -> root.put(key, value.copy()));
    }
    public static void put(ItemStack stack, String key, Tag value) {
        update(stack, root -> root.put(key, value.copy()));
    }
    public static void putElement(ItemStack stack, String element, String key, Tag value) {
        updateElement(stack, element, child -> child.put(key, value.copy()));
    }
}
