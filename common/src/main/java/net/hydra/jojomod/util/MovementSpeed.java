package net.hydra.jojomod.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** Movement values shared by stand controls and their user. */
public final class MovementSpeed {
    private MovementSpeed() {}

    public static float sneaking(LivingEntity entity) {
        // In 1.21 this is the complete multiplier, including Swift Sneak, not a bonus to 0.3.
        var attribute = entity.getAttribute(Attributes.SNEAKING_SPEED);
        // Stand powers can also belong to mobs, which do not register player-only attributes.
        return attribute == null ? 0.3F : Mth.clamp((float) attribute.getValue(), 0.0F, 1.0F);
    }
}
