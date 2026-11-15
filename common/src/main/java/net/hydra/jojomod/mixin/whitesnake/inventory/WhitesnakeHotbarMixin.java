package net.hydra.jojomod.mixin.whitesnake.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.hydra.jojomod.event.powers.whitesnake.WhitesnakeControlInventory;
import net.hydra.jojomod.entity.stand.WhitesnakeEntity;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class WhitesnakeHotbarMixin {
    @Unique private static final ResourceLocation WHITESNAKE_HOTBAR =
            ResourceLocation.withDefaultNamespace("hud/hotbar");
    @Unique private static final ResourceLocation WHITESNAKE_SELECTION =
            ResourceLocation.withDefaultNamespace("hud/hotbar_selection");

    // Replace only the item row; the outer layer still renders stand overlays and meters.
    @Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
    private void roundaboutWhitesnake$renderHotbar(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) return;
        WhitesnakeEntity stand = WhitesnakeControlInventory.controlledStand(player);
        if (stand == null) return;
        if (stand.isMeltingModeActive()) {
            ci.cancel();
            return;
        }
        int x = graphics.guiWidth() / 2;
        int y = graphics.guiHeight() - 22;
        RenderSystem.enableBlend();
        graphics.blitSprite(WHITESNAKE_HOTBAR, x - 91, y, 182, 22);
        graphics.blitSprite(WHITESNAKE_SELECTION,
                x - 92 + player.getInventory().selected * 20, y - 1, 24, 23);
        for (int slot = 0; slot < WhitesnakeControlInventory.SIZE; slot++) {
            ItemStack stack = WhitesnakeControlInventory.get(player).get(slot);
            if (stack.isEmpty()) continue;
            int itemX = x - 90 + slot * 20 + 2;
            int itemY = y + 3;
            graphics.renderItem(player, stack, itemX, itemY, slot);
            graphics.renderItemDecorations(minecraft.font, stack, itemX, itemY);
        }
        RenderSystem.disableBlend();
        ci.cancel();
    }
}
