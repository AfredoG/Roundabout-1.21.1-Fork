package net.hydra.jojomod.mixin.access;

import net.hydra.jojomod.access.SynchedDataAccess;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SynchedEntityData.class, SynchedEntityData.Builder.class})
public interface AccessSynchedData extends SynchedDataAccess {
    @Override
    @Accessor("itemsById")
    SynchedEntityData.DataItem<?>[] roundabout$dataItems();
}
