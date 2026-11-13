package net.hydra.jojomod.mixin.forge;

import net.hydra.jojomod.access.AccessFateFoodData;
import net.hydra.jojomod.event.index.FateTypes;
import net.hydra.jojomod.event.index.PowerTypes;
import net.hydra.jojomod.util.FoodValues;
import net.hydra.jojomod.util.ItemData;
import net.hydra.jojomod.util.MainUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class ForgeEatingMixin {
    // Keep consumption, sounds and statistics in Player.eat; replace only food replenishment.
    @Redirect(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V"))
    private void roundabout$eatForge(FoodData foodData, FoodProperties props,
                                     Level level, ItemStack stack, FoodProperties original) {
        Player player = (Player) (Object) this;
        ((AccessFateFoodData) foodData).rdbt$setPlayer(player);
        if (PowerTypes.isErasingTime(player)) return;

        boolean blood = FateTypes.hasBloodHunger(player);
        boolean enhanced = ItemData.copy(stack).contains("pearljamfood");
        if (!blood && !enhanced) {
            foodData.eat(props);
            return;
        }
        int nutrition = blood ? MainUtil.getBloodAmount(stack) : props.nutrition();
        float modifier = blood ? MainUtil.getSaturationAmount(stack)
                : FoodValues.saturationModifier(props.nutrition(), props.saturation());
        if (enhanced) {
            nutrition = FoodValues.pearlJamNutrition(nutrition);
            modifier = FoodValues.pearlJamSaturationModifier(modifier);
        }
        foodData.eat(nutrition, modifier);
    }
}
