package net.hydra.jojomod.util;

import net.hydra.jojomod.access.SynchedDataAccess;
import net.minecraft.network.syncher.EntityDataAccessor;

public final class TrackedData {
    private TrackedData() {}
    public static boolean has(Object data, EntityDataAccessor<?> key) {
        if (data == null) return false;
        var items = ((SynchedDataAccess) data).roundabout$dataItems();
        return key.id() >= 0 && key.id() < items.length && items[key.id()] != null;
    }
}
