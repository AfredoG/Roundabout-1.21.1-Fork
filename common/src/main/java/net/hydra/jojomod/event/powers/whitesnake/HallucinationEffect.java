package net.hydra.jojomod.event.powers.whitesnake;

import net.hydra.jojomod.access.IMob;
import net.hydra.jojomod.event.index.PacketDataIndex;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.item.ModItems;
import net.hydra.jojomod.util.S2CPacketUtil;
import net.hydra.jojomod.util.config.Config;

import net.hydra.jojomod.client.ClientNetworking;

import net.hydra.jojomod.event.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.hydra.jojomod.Roundabout;
import net.minecraft.resources.ResourceLocation;

public final class HallucinationEffect extends MobEffect {
    public static final int MAX_LEVEL = 3;
    private static final int CONFUSION_PULSE_TICKS = 7;
    private static final ResourceLocation SLOWNESS_ID = ResourceLocation.fromNamespaceAndPath(
            Roundabout.MOD_ID, "hallucination_movement_slowdown");

    public HallucinationEffect() {
        super(MobEffectCategory.HARMFUL, 0xEEEAF7);
    }

    public static MobEffectInstance createInstance(int duration, int amplifier) {
        Config.WhitesnakeSettings config = ClientNetworking.getAppropriateConfig().whitesnakeSettings;
        amplifier = Math.min(MAX_LEVEL - 1, Math.max(0, amplifier));
        return new MobEffectInstance(
                ModEffects.HALLUCINATION,
                duration, amplifier, false, !config.hideHallucinationParticles,
                !config.hallucinationHidesEffects);
    }

    public static boolean hasDistortion(MobEffectInstance effect) {
        return effect != null;
    }

    // Called from LivingEntity's lifecycle hooks, which still provide the entity in 1.21.1.
    public static void onLevelChanged(LivingEntity living, int amplifier) {
        if (living.level().isClientSide()) return;
        updateMovementModifier(living, amplifier);
        syncIndicator(living, amplifier + 1);
    }

    public static void onRemoved(LivingEntity living) {
        if (living.level().isClientSide()) return;
        syncIndicator(living, 0);
    }

    private static void updateMovementModifier(LivingEntity living, int amplifier) {
        AttributeInstance movement = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement == null) return;
        boolean slowed = amplifier >= 1 && (amplifier < 2 || living instanceof Player);
        if (!slowed) {
            movement.removeModifier(SLOWNESS_ID);
        } else if (movement.getModifier(SLOWNESS_ID) == null) {
            movement.addTransientModifier(new AttributeModifier(SLOWNESS_ID,
                    -0.06D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributes) {
        super.removeAttributeModifiers(attributes);
        AttributeInstance movement = attributes.getInstance(Attributes.MOVEMENT_SPEED);
        if (movement != null) movement.removeModifier(SLOWNESS_ID);
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int amplifier) {
        if (living.level().isClientSide()) return true;
        MobEffectInstance hallucination = living.getEffect(ModEffects.HALLUCINATION);
        if (hallucination == null) return true;
        // Re-establish the transient modifier after loading an entity from disk.
        updateMovementModifier(living, amplifier);
        int duration = hallucination.getDuration();
        if (!living.level().isClientSide() && living.tickCount % 20 == 0) {
            syncIndicator(living, amplifier + 1);
        }
        if (!living.level().isClientSide() && living instanceof Mob mob && amplifier < 2) {
            int interval = amplifier == 1 ? 60 : 120;
            if (living.tickCount % interval == 0) {
                IMob mobData = (IMob) mob;
                mobData.roundabout$setConfusionTicks(Math.max(
                        mobData.roundabout$getConfusionTicks(), CONFUSION_PULSE_TICKS));
            }
        }
        if (amplifier >= 2) {
            MobEffectInstance sleeping = living.getEffect(
                    ModEffects.SLEEPING);
            if (sleeping == null || sleeping.getDuration() < duration - 1) {
                living.addEffect(new MobEffectInstance(ModEffects.SLEEPING, duration, 0, false, false, true));
            }
        }
        if (ClientNetworking.getAppropriateConfig().whitesnakeSettings.hallucinationAppliesMelting) {
            MobEffectInstance melting = living.getEffect(ModEffects.MELTING);
            if (melting == null || melting.getAmplifier() < amplifier
                    || melting.getAmplifier() == amplifier && melting.getDuration() < duration - 1) {
                living.addEffect(new MobEffectInstance(ModEffects.MELTING, duration, amplifier));
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private static void syncIndicator(LivingEntity target, int level) {
        if (!(target.level() instanceof ServerLevel serverLevel)) return;
        for (ServerPlayer viewer : serverLevel.players()) {
            if (viewer.distanceToSqr(target) > 400) continue;
            ItemStack standDisc = ((StandUser) viewer).roundabout$getStandDisc();
            if (standDisc.is(ModItems.STAND_DISC_WHITESNAKE)
                    || standDisc.is(ModItems.MAX_STAND_DISC_WHITESNAKE)) {
                S2CPacketUtil.sendGenericIntIntToClientPacket(viewer,
                        PacketDataIndex.S2C_HALLUCINATION_INDICATOR, target.getId(), level);
            }
        }
    }
}
