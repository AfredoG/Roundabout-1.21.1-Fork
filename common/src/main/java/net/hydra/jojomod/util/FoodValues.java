package net.hydra.jojomod.util;

/** Conversion at the 1.21 boundary: food components store saturation points, not a multiplier. */
public final class FoodValues {
    private FoodValues() {}

    public static float saturationModifier(int nutrition, float saturation) {
        // Zero-nutrition food had zero saturation in the old multiplier-based API.
        return nutrition > 0 ? saturation / (nutrition * 2.0F) : 0.0F;
    }

    public static int pearlJamNutrition(int nutrition) {
        return (int) Math.min(nutrition * 1.5, 6);
    }

    public static float pearlJamSaturationModifier(float modifier) {
        return Math.min(modifier * 1.5F, 1.2F);
    }
}
