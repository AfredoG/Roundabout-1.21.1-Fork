package net.hydra.jojomod.mixin.data_and_structures;

import net.hydra.jojomod.access.ILootPool;
import net.hydra.jojomod.access.ILootTable;
import net.hydra.jojomod.block.ModBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

    @Inject(method = "addRandomItem", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void roundabout$addLootEntry(Consumer<ItemStack> consumer, LootContext context, CallbackInfo ci,
                                       RandomSource randomSource, List<LootPoolEntry> list, MutableInt totalWeight) {
        if (roundabout$doesLootTableContain(context.getLevel(), BuiltInLootTables.FISHING_TREASURE)) {
            // Keep the candidate list and weight sum consistent before vanilla checks either one.
            LootItem.lootTableItem(ModBlocks.EQUIPPABLE_STONE_MASK_BLOCK).setWeight(1).build().expand(context, entry -> {
                int weight = entry.getWeight(context.getLuck());
                if (weight > 0) {
                    list.add(entry);
                    totalWeight.add(weight);
                }
            });
        }
    }

    @Unique
    private boolean roundabout$doesLootTableContain(Level level, ResourceKey<LootTable> location) {
        if (level.getServer() == null) return false;
        LootTable table = level.getServer().reloadableRegistries().getLootTable(location);
        return table != LootTable.EMPTY && ((ILootTable) table).roundabout$getPools().contains(this);
    }
}
