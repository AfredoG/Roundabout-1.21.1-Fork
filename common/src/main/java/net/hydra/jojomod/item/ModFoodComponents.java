package net.hydra.jojomod.item;

import net.hydra.jojomod.event.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodComponents {
    public static final FoodProperties COFFEE_GUM = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties FLESH_CHUNK = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER,400),1.0F)
            .build();
    public static final FoodProperties LOCACACA = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f)
            .alwaysEdible().build();
}
