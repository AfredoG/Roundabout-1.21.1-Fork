package net.hydra.jojomod.access;

import net.minecraft.network.syncher.SynchedEntityData;

/** Read-only view used to preserve upstream initialization guards on 1.21.1. */
public interface SynchedDataAccess {
    SynchedEntityData.DataItem<?>[] roundabout$dataItems();
}
