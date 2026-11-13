package net.hydra.jojomod.event.powers.whitesnake.disc;

import net.hydra.jojomod.access.DiscBearer;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.stand.powers.PowersWhitesnake;
import net.hydra.jojomod.util.S2CPacketUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.hydra.jojomod.util.MusicDiscData;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import net.hydra.jojomod.sound.ModSounds;

import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.WeakHashMap;

public final class MusicDiscController {
    private static final Map<LivingEntity, Holder<JukeboxSong>> PLAYING = new WeakHashMap<>();
    private static final Map<LivingEntity, Set<ServerPlayer>> LISTENERS = new WeakHashMap<>();

    private MusicDiscController() {
    }

    public static boolean implant(ItemStack stack, LivingEntity target, LivingEntity thrower) {
        if (!MusicDiscData.isDisc(stack)
                || WhitesnakeDiscUtil.isDiscBlacklisted(target)) return false;
        if (target.level().isClientSide()) return true;
        DiscBearer bearer = (DiscBearer) target;
        if (!bearer.roundabout$getMusicDisc().isEmpty()) return false;
        ItemStack implanted = stack.copy();
        implanted.setCount(1);
        bearer.roundabout$setMusicDisc(implanted);
        target.level().playSound(null, target.blockPosition(), ModSounds.WHITESNAKE_DISC_INSERT_EVENT,
                SoundSource.PLAYERS, 1.0F, 1.0F);
        start(target, implanted);
        return true;
    }

    public static void tick(LivingEntity bearerEntity) {
        if (bearerEntity.level().isClientSide()) return;
        ItemStack stack = ((DiscBearer) bearerEntity).roundabout$getMusicDisc();
        if (stack.isEmpty() || !MusicDiscData.isDisc(stack)) {
            if (PLAYING.containsKey(bearerEntity)) stop(bearerEntity);
            return;
        }
        var song = MusicDiscData.song(stack, bearerEntity.registryAccess());
        if (song.isEmpty()) {
            if (PLAYING.containsKey(bearerEntity)) stop(bearerEntity);
            return;
        }
        if (!java.util.Objects.equals(PLAYING.get(bearerEntity), song.get()) || bearerEntity.tickCount % 20 == 0) {
            start(bearerEntity, stack);
        }
    }

    public static void startTracking(LivingEntity entity, ServerPlayer player) {
        Set<ServerPlayer> listeners = LISTENERS.get(entity);
        if (listeners != null) listeners.remove(player);
        start(entity, ((DiscBearer) entity).roundabout$getMusicDisc());
    }

    public static void ejectOnDamage(LivingEntity bearerEntity) {
        if (bearerEntity.level().isClientSide()) return;
        DiscBearer bearer = (DiscBearer) bearerEntity;
        ItemStack stack = bearer.roundabout$getMusicDisc();
        if (stack.isEmpty()) return;
        stop(bearerEntity);
        bearer.roundabout$setMusicDisc(ItemStack.EMPTY);
        bearerEntity.spawnAtLocation(stack.copy());
        bearerEntity.level().playSound(null, bearerEntity.blockPosition(), ModSounds.WHITESNAKE_DISC_EJECT_EVENT,
                SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private static void start(LivingEntity entity, ItemStack stack) {
        if (!(entity.level() instanceof ServerLevel server)) return;
        var resolved = MusicDiscData.song(stack, server.registryAccess());
        if (resolved.isEmpty()) return;
        Holder<JukeboxSong> song = resolved.get();
        if (!java.util.Objects.equals(PLAYING.get(entity), song)) {
            if (PLAYING.containsKey(entity)) stop(entity);
            PLAYING.put(entity, song);
        }
        Set<ServerPlayer> listeners = LISTENERS.computeIfAbsent(entity,
                target -> Collections.newSetFromMap(new WeakHashMap<>()));
        SoundEvent sound = song.value().soundEvent().value();
        double range = sound.getRange(4.0F);
        for (ServerPlayer player : server.players()) {
            if (listeners.contains(player)) continue;
            if (player.distanceToSqr(entity) > range * range) {
                if (!(((StandUser) player).roundabout$getStandPowers() instanceof PowersWhitesnake powers)
                        || !powers.isPiloting()) continue;
                LivingEntity stand = powers.getPilotingStand();
                if (stand == null || !stand.isAlive() || stand.isRemoved()
                        || stand.distanceToSqr(entity) > range * range) continue;
            }
            S2CPacketUtil.sendMusicDiscPacket(player, entity.getId(), sound.getLocation().toString());
            listeners.add(player);
        }
    }

    private static void stop(LivingEntity entity) {
        if (!(entity.level() instanceof ServerLevel server)) return;
        PLAYING.remove(entity);
        LISTENERS.remove(entity);
        server.players().forEach(player -> S2CPacketUtil.sendMusicDiscPacket(player, entity.getId(), ""));
    }
}
