package net.hydra.jojomod.mixin.gravity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.stand.powers.PowersWhiteAlbum;
import net.hydra.jojomod.util.gravity.GravityAPI;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerPlayer.class)
public abstract class GravityServerPlayerStatisticsMixin {
    @WrapMethod(method = "checkMovementStatistics")
    private void roundabout$localMovementStatistics(double x, double y, double z, Operation<Void> original) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        Vec3 local = RotationUtil.vecWorldToPlayer(x, y, z, GravityAPI.getGravityDirection(player));
        original.call(local.x, local.y, local.z);
    }

    @ModifyArg(method = "checkMovementStatistics", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerPlayer;causeFoodExhaustion(F)V"), index = 0)
    private float roundabout$skatingExhaustion(float exhaustion) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        if (((StandUser)player).roundabout$getStandPowers() instanceof PowersWhiteAlbum album
                && album.hasSkatesActivated() && !player.isInWater()) {
            return exhaustion * 0.3F;
        }
        return exhaustion;
    }
}
