package net.hydra.jojomod.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.authlib.GameProfile;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;

/** Client-side skin access shared by disguises, clones and detached body parts. */
public final class SkinUtil {
    private static final Cache<UUID, CompletableFuture<GameProfile>> profiles = CacheBuilder.newBuilder()
            .maximumSize(256).expireAfterWrite(Duration.ofMinutes(10)).build();

    private SkinUtil() {}
    public record SkinData(ResourceLocation texture, boolean slim) {}

    public static SkinData getSkin(GameProfile profile) {
        PlayerSkin skin = getPlayerSkin(profile);
        return new SkinData(skin.texture(), skin.model() == PlayerSkin.Model.SLIM);
    }

    public static PlayerSkin getPlayerSkin(GameProfile profile) {
        if (profile == null) {
            return new PlayerSkin(DefaultPlayerSkin.getDefaultTexture(), null, null, null,
                    PlayerSkin.Model.WIDE, true);
        }
        Minecraft minecraft = Minecraft.getInstance();
        // SkinManager now decodes packed textures only. Resolve UUID-only disguises asynchronously.
        if (!profile.getProperties().containsKey("textures") && profile.getId().version() != 3) {
            CompletableFuture<GameProfile> request = profiles.asMap().computeIfAbsent(profile.getId(), id ->
                    CompletableFuture.supplyAsync(() -> {
                        var result = minecraft.getMinecraftSessionService().fetchProfile(id, true);
                        return result == null ? null : result.profile();
                    }, Util.nonCriticalIoPool()).exceptionally(error -> null));
            GameProfile resolved = request.getNow(null);
            if (resolved != null) profile = resolved;
        }
        return minecraft.getSkinManager().getInsecureSkin(profile);
    }

    /** Visages replace only the texture, retaining the original cape, elytra and arm model. */
    public static PlayerSkin withTexture(PlayerSkin original, ResourceLocation texture) {
        return new PlayerSkin(texture, null, original.capeTexture(), original.elytraTexture(),
                original.model(), false);
    }
}
