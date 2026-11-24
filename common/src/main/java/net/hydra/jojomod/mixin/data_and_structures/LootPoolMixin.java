package net.hydra.jojomod.mixin.data_and_structures;

import net.hydra.jojomod.access.ILootPool;
import net.hydra.jojomod.access.ILootTable;
import net.hydra.jojomod.block.ModBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Consumer;

@Mixin(LootPool.class)
public abstract class LootPoolMixin implements ILootPool {
    @Shadow @Final
    private List<LootPoolEntryContainer> entries;

    @Override
    public int roundabout$getSize() {
        return this.entries.size();
    }

    @com.llamalad7.mixinextras.injector.ModifyExpressionValue(method = "addRandomItem",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/storage/loot/LootPool;entries:Ljava/util/List;"))
    private List<LootPoolEntryContainer> roundabout$addLootEntry(List<LootPoolEntryContainer> original,
                                                               Consumer<ItemStack> consumer, LootContext context) {
        if (!roundabout$doesLootTableContain(context.getLevel(), BuiltInLootTables.FISHING_TREASURE)) {
            return original;
        }
        // A per-roll view keeps frozen pool data intact. Native expansion calculates both
        // candidates and their weight sum, without relying on compiler-specific locals.
        List<LootPoolEntryContainer> entries = new java.util.ArrayList<>(original);
        entries.add(LootItem.lootTableItem(ModBlocks.EQUIPPABLE_STONE_MASK_BLOCK).setWeight(1).build());
        return entries;
    }

    @Unique
    private boolean roundabout$doesLootTableContain(Level level, ResourceKey<LootTable> location) {
        if (level.getServer() == null) return false;
        LootTable table = level.getServer().reloadableRegistries().getLootTable(location);
        return table != LootTable.EMPTY && ((ILootTable) table).roundabout$getPools().contains(this);
    }
}
