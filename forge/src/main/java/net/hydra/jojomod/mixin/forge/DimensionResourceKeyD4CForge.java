package net.hydra.jojomod.mixin.forge;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugScreenOverlay.class)
public abstract class DimensionResourceKeyD4CForge {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "getGameInformation", at = @At("RETURN"), cancellable = true)
    private void roundabout$spoofDimensionName(CallbackInfoReturnable<List<String>> cir) {
        if (this.minecraft.level == null) return;
        ResourceLocation dimension = this.minecraft.level.dimension().location();
        if (!dimension.getNamespace().equals("roundabout") || !dimension.getPath().startsWith("d4c-")) return;

        // Keep the current game's diagnostics; disguise only the dimension line.
        String prefix = dimension + " FC: ";
        List<String> lines = new ArrayList<>(cir.getReturnValue());
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(prefix)) {
                lines.set(i, Level.OVERWORLD.location() + lines.get(i).substring(dimension.toString().length()));
            }
        }
        cir.setReturnValue(lines);
    }
}
