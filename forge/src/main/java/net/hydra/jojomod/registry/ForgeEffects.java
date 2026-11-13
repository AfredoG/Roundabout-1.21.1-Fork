package net.hydra.jojomod.registry;

import net.hydra.jojomod.Roundabout;
import net.minecraft.resources.ResourceLocation;
import net.hydra.jojomod.event.powers.ReconstructionEffect;
import net.hydra.jojomod.event.powers.whitesnake.HallucinationEffect;
import net.hydra.jojomod.event.powers.whitesnake.OldEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ForgeEffects{

    public static final DeferredRegister<MobEffect> POTION_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Roundabout.MOD_ID);
    public static final DeferredHolder<MobEffect, MobEffect> BLEED =
            POTION_EFFECTS.register("bleed", () ->
                    new Effect(MobEffectCategory.HARMFUL, 11994666)
            );
    public static final DeferredHolder<MobEffect, MobEffect> STITCHED =
            POTION_EFFECTS.register("stitched", () ->
                    new Effect(MobEffectCategory.NEUTRAL, 11994666)
            );
    public static final DeferredHolder<MobEffect, MobEffect> HEX =
            POTION_EFFECTS.register("hex", () ->
                    new Effect(MobEffectCategory.HARMFUL, 11606258)
            );
    public static final DeferredHolder<MobEffect, MobEffect> BANISH =
            POTION_EFFECTS.register("banish", () ->
                    new Effect(MobEffectCategory.HARMFUL, 15099838)
            );
    public static final DeferredHolder<MobEffect, MobEffect> DOOMED =
            POTION_EFFECTS.register("doom", () ->
                    new Effect(MobEffectCategory.HARMFUL, 0)
            );
    public static final DeferredHolder<MobEffect, MobEffect> DOOM_IMMUNITY =
            POTION_EFFECTS.register("doom_immunity", () ->
                    new Effect(MobEffectCategory.BENEFICIAL, 0)
            );
    public static final DeferredHolder<MobEffect, MobEffect> SWITCH =
            POTION_EFFECTS.register("switch", () ->
                    new Effect(MobEffectCategory.HARMFUL, 9974431)
            );
    public static final DeferredHolder<MobEffect, MobEffect> STAND_VIRUS =
            POTION_EFFECTS.register("stand_virus", () ->
                    new Effect(MobEffectCategory.HARMFUL, 9979490)
            );
    public static final DeferredHolder<MobEffect, MobEffect> CAPTURING_LOVE =
            POTION_EFFECTS.register("capturing_love", () ->
                    new Effect(MobEffectCategory.BENEFICIAL, 16772988)
            );
    public static final DeferredHolder<MobEffect, MobEffect> FACELESS =
            POTION_EFFECTS.register("faceless", () ->
                    new Effect(MobEffectCategory.HARMFUL, 10329495)
            );
    public static final DeferredHolder<MobEffect, MobEffect> MELTING =
            POTION_EFFECTS.register("melting", () ->
                    new Effect(MobEffectCategory.HARMFUL, 10329495)
                            .addAttributeModifier(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID, "melting_health"),-0.7, AttributeModifier.Operation.ADD_VALUE)
            );
    public static final DeferredHolder<MobEffect, MobEffect> STAND_MELTING =
            POTION_EFFECTS.register("stand_melting", () ->
                    new Effect(MobEffectCategory.HARMFUL, 10329495)
            );
    public static final DeferredHolder<MobEffect, MobEffect> GRAVITY_FLIP =
            POTION_EFFECTS.register("gravity_flip", () ->
                    new Effect(MobEffectCategory.HARMFUL, 4906114));
    public static final DeferredHolder<MobEffect, MobEffect> WARDING =
            POTION_EFFECTS.register("warding", () ->
                    new Effect(MobEffectCategory.BENEFICIAL, 16760283));
    public static final DeferredHolder<MobEffect, MobEffect> VAMPIRE_BLOOD =
            POTION_EFFECTS.register("vampire_blood", () ->
                    new Effect(MobEffectCategory.HARMFUL, 8662571));
    public static final DeferredHolder<MobEffect, MobEffect> SINGE =
            POTION_EFFECTS.register("singe", () ->
                    new Effect(MobEffectCategory.HARMFUL, 15428404));
    public static final DeferredHolder<MobEffect, MobEffect> UNBALANCED =
            POTION_EFFECTS.register("unbalanced", () ->
                    new Effect(MobEffectCategory.HARMFUL, 2738639));
    public static final DeferredHolder<MobEffect, MobEffect> MOLD =
            POTION_EFFECTS.register("mold", () ->
                    new Effect(MobEffectCategory.HARMFUL, 0));
    public static final DeferredHolder<MobEffect, MobEffect> CRIPPLED =
            POTION_EFFECTS.register("crippled", () ->
                    new Effect(MobEffectCategory.HARMFUL, 725255)
                            .addAttributeModifier(Attributes.MOVEMENT_SPEED,ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID, "crippled_speed"),-0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> HALLUCINATION =
            POTION_EFFECTS.register("hallucination", HallucinationEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> OLD = POTION_EFFECTS.register("old", OldEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SLEEPING = POTION_EFFECTS.register("sleeping", () ->
            new Effect(MobEffectCategory.HARMFUL, 0x9BA9E8));
    public static final DeferredHolder<MobEffect, MobEffect> REFRESHED =
            POTION_EFFECTS.register("refreshed", () ->
                    new Effect(MobEffectCategory.BENEFICIAL, 13302495)
            );
    public static final DeferredHolder<MobEffect, MobEffect> INFINITE_SPIN =
            POTION_EFFECTS.register("infinite_spin", () ->
                    new Effect(MobEffectCategory.HARMFUL, 16411584)
            );
    public static final DeferredHolder<MobEffect, MobEffect> HAZE_VIRUS =
            POTION_EFFECTS.register("haze_virus", () ->
                    new Effect(MobEffectCategory.HARMFUL, 10824365)
            );
    public static final DeferredHolder<MobEffect, MobEffect> DISTORTION_VIRUS =
            POTION_EFFECTS.register("distortion_virus", () ->
                    new Effect(MobEffectCategory.HARMFUL, 10824365)
            );
    public static final DeferredHolder<MobEffect, MobEffect> VIRUS_IMMUNITY =
            POTION_EFFECTS.register("virus_immunity", () ->
                    new Effect(MobEffectCategory.BENEFICIAL, 10824365)
            );
    public static final DeferredHolder<MobEffect, MobEffect> IMPRINTING =
            POTION_EFFECTS.register("imprinting", () ->
                    new Effect(MobEffectCategory.NEUTRAL, 16134815)
            );
    public static final DeferredHolder<MobEffect, MobEffect> SWAPPED =
            POTION_EFFECTS.register("swapped", () ->
                    new Effect(MobEffectCategory.NEUTRAL, 16134815).
                            addAttributeModifier(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(Roundabout.MOD_ID, "swapped_health"),-2, AttributeModifier.Operation.ADD_VALUE)
            );
    public static final DeferredHolder<MobEffect, MobEffect> FORTIFICATION =
            POTION_EFFECTS.register("fortification", () ->
                    new Effect(MobEffectCategory.BENEFICIAL, 3137792)
            );
    public static final DeferredHolder<MobEffect, MobEffect> RECONSTRUCTION = POTION_EFFECTS.register("reconstruction", ReconstructionEffect::new);

    public static class Effect extends MobEffect{
        public Effect(MobEffectCategory typeIn, int liquidColorIn) {

            super(typeIn, liquidColorIn);
        }

    }

}
