package net.hydra.jojomod.mixin.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.client.StandIcons;
import net.hydra.jojomod.event.index.FateTypes;
import net.hydra.jojomod.event.index.PowerTypes;
import net.hydra.jojomod.event.powers.StandUser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// NeoForge 1.21.1 adds HUD layers directly to Gui; ForgeGui no longer exists.
@Mixin(Gui.class)
public abstract class ForgeForgeGui {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private RandomSource random;
    @Shadow private int tickCount;
    @Shadow(remap = false) public int rightHeight;

    @Shadow
    private Player getCameraPlayer() {
        throw new AssertionError();
    }

    @Inject(method = "renderFood(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;II)V",
            at = @At("HEAD"), cancellable = true)
    private void roundabout$renderFood(GuiGraphics graphics, Player player, int top, int right, CallbackInfo ci) {
        if (!FateTypes.hasBloodHunger(player)) return;
        ci.cancel();
        RenderSystem.enableBlend();
        ClientUtil.renderHungerStuff(graphics, player, right, top,
                random.nextInt(3), player.getFoodData().getFoodLevel(), tickCount);
        RenderSystem.disableBlend();
        // The caller, renderFoodLevel, owns profiling and the rightHeight increment.
    }

    @Inject(method = "renderAirLevel(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At("TAIL"), remap = false)
    private void roundabout$renderAir(GuiGraphics graphics, CallbackInfo ci) {
        Player player = getCameraPlayer();
        if (player == null || minecraft.level == null || !PowerTypes.hasStandActive(player)) return;
        StandUser standUser = (StandUser) player;
        int oxygen = standUser.roundabout$getStandPowers().getAirAmount();
        int capacity = standUser.roundabout$getStandPowers().getMaxAirAmount();
        if (oxygen < 0 || capacity <= 0) return;

        boolean needsAir = !player.canBreatheUnderwater() && !MobEffectUtil.hasWaterBreathing(player)
                && !player.getAbilities().invulnerable;
        if (!(player.isEyeInFluid(FluidTags.WATER) && needsAir) && oxygen >= capacity) return;

        // NeoForge has already reserved space for food, vehicle hearts and vanilla air.
        int left = graphics.guiWidth() / 2 + 6;
        // A 15px bar needs 6px more above the next standard 9px HUD row.
        int top = graphics.guiHeight() - rightHeight - 6;
        int airWidth = Mth.floor(81.0D * Mth.clamp(oxygen, 0, capacity) / capacity);
        RenderSystem.enableBlend();
        if (oxygen > 0) {
            graphics.blit(StandIcons.JOJO_ICONS, left, top, 165, 171, 4 + airWidth, 15);
        }
        graphics.blit(StandIcons.JOJO_ICONS, left, top, 165, 186, 89, 15);
        RenderSystem.disableBlend();
        rightHeight += 15;
    }
}
