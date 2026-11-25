package net.hydra.jojomod.mixin.gui;

import net.hydra.jojomod.client.gui.diverdown.custom_workbench_texture.DiverDownStonecutterScreen;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StonecutterScreen.class)
public class DiverDownStonecutterScreenMixin {

    @Redirect(method = "renderBg", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/StonecutterScreen;BG_LOCATION:Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation roundabout$diverDownStonecutterBg() {
        if ((Object) this instanceof DiverDownStonecutterScreen) {
            return DiverDownStonecutterScreen.TEXTURE;
        }
        return ResourceLocation.parse("textures/gui/container/stonecutter.png");
    }

    @com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation(method = {"renderBg", "renderButtons"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private void roundabout$customSprites(net.minecraft.client.gui.GuiGraphics graphics, ResourceLocation sprite,
                                          int x, int y, int width, int height,
                                          com.llamalad7.mixinextras.injector.wrapoperation.Operation<Void> original) {
        if ((Object) this instanceof DiverDownStonecutterScreen) {
            int uv = switch (sprite.getPath()) {
                case "container/stonecutter/scroller" -> 176 << 16;
                case "container/stonecutter/scroller_disabled" -> 188 << 16;
                case "container/stonecutter/recipe" -> 166;
                case "container/stonecutter/recipe_selected" -> 184;
                case "container/stonecutter/recipe_highlighted" -> 202;
                default -> -1;
            };
            if (uv >= 0) {
                graphics.blit(DiverDownStonecutterScreen.TEXTURE, x, y, uv >>> 16, uv & 65535, width, height);
                return;
            }
        }
        original.call(graphics, sprite, x, y, width, height);
    }
}
