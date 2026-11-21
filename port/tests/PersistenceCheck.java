package net.hydra.jojomod.block;

import java.util.List;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

/** Run against the real 1.21.1 classes without loading the incomplete mod. */
public final class PersistenceCheck {
    private static int checks;

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        checks++;
    }

    public static void main(String[] args) {
        net.neoforged.fml.loading.LoadingModList.of(List.of(), List.of(), List.of(), List.of(), Map.of());
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var registries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);

        // Components and sparse inventory slots survive the registry-aware format.
        ItemStack item = new ItemStack(Items.IRON_SWORD);
        item.setDamageValue(17);
        item.set(DataComponents.CUSTOM_NAME, Component.literal("Stored stand data"));
        CompoundTag memory = new CompoundTag();
        memory.putInt("Experience", 125);
        item.set(DataComponents.CUSTOM_DATA, CustomData.of(memory));
        ItemStack restored = ItemStack.parseOptional(registries, (CompoundTag) item.saveOptional(registries));
        check(ItemStack.matches(item, restored), "Item components changed during persistence");
        check(ItemStack.parseOptional(registries, (CompoundTag) ItemStack.EMPTY.saveOptional(registries)).isEmpty(),
                "Empty item did not round-trip");
        NonNullList<ItemStack> slots = NonNullList.withSize(9, ItemStack.EMPTY);
        slots.set(2, item);
        slots.set(8, new ItemStack(Items.APPLE, 3));
        CompoundTag inventory = ContainerHelper.saveAllItems(new CompoundTag(), slots, registries);
        NonNullList<ItemStack> loaded = NonNullList.withSize(9, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(inventory, loaded, registries);
        for (int i = 0; i < slots.size(); i++) {
            check(ItemStack.matches(slots.get(i), loaded.get(i)), "Inventory slot shifted: " + i);
        }

        // Removing a protection block must remain effective after a save/reload.
        ProtectionSavedData data = new ProtectionSavedData();
        BlockPos first = new BlockPos(-32, 64, -32);
        BlockPos second = first.offset(300, 0, 0);
        data.add(first);
        data.add(second);
        check(data.isDirty(), "Adding a protection did not mark data dirty");
        data = ProtectionSavedData.load(data.save(new CompoundTag(), registries), registries);
        check(data.isProtected(first), "First protection was lost");
        check(data.isProtected(second), "Second protection was lost");
        check(data.isProtected(first.offset(-199, 0, 0)), "Cross-chunk radius was lost");
        check(!data.isProtected(first.offset(-200, 0, 0)), "Exclusive radius boundary changed");
        data.remove(first);
        check(!data.isProtected(first), "Removed protection is still active");
        check(data.isProtected(first.offset(150, 0, 0)), "Overlapping protection was removed");
        check(data.isDirty(), "Removing a protection did not mark data dirty");
        data = ProtectionSavedData.load(data.save(new CompoundTag(), registries), registries);
        check(!data.isProtected(first), "Removed protection returned after reload");
        check(data.isProtected(second), "Unrelated protection was lost after reload");
        data.remove(second);
        data = ProtectionSavedData.load(data.save(new CompoundTag(), registries), registries);
        check(!data.isProtected(second), "Last removed protection returned after reload");
        System.out.println("PASS: " + checks + " persistence cases");
    }
}
