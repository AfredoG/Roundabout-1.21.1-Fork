package net.hydra.jojomod.mixin.forge;

import net.hydra.jojomod.client.gui.NoCancelInputScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.extensions.IKeyMappingExtension;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(KeyMapping.class)
public abstract class ForgeKillsScreenInputsFix implements IKeyMappingExtension {
    @Override
    public boolean isConflictContextAndModifierActive() {
        // (neo)forge does extra checks that make some keys not work in inventories
        // I don't really know/care how it works since Fabric doesn't have this
        // so when the mod needs raw key down, skip the extra check
        if (Minecraft.getInstance().screen instanceof NoCancelInputScreen) {
            return true;
        } else {
            return IKeyMappingExtension.super.isConflictContextAndModifierActive();
        }
    }
}