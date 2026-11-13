package net.hydra.jojomod.mixin.networking;

import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.stand.powers.PowersCatchTheRainbow;
import net.hydra.jojomod.stand.powers.PowersCream;
import net.hydra.jojomod.stand.powers.PowersGreenDay;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.zetalasis.networking.packet.api.IClientNetworking;
import net.zetalasis.networking.packet.api.args.c2s.AbstractBaseC2SPacket;
import net.zetalasis.networking.packet.api.args.c2s.PacketArgsC2S;
import net.zetalasis.networking.packet.impl.ModNetworking;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// C2S Networking
@Mixin(ServerGamePacketListenerImpl.class)
public class ZServerGamePacketListenerImpl {
    @Shadow public ServerPlayer player;

    @Inject(method = "handlePlayerAction", at = @At("HEAD"), cancellable = true)
    private void roundabout$handlePlayerAction(ServerboundPlayerActionPacket $$0, CallbackInfo ci) {
        ServerboundPlayerActionPacket.Action serverboundplayeractionpacket$action = $$0.getAction();
        switch (serverboundplayeractionpacket$action) {
            case SWAP_ITEM_WITH_OFFHAND:
                if (((StandUser)this.player).roundabout$getEffectiveCombatMode() || ((StandUser) this.player).roundabout$isPossessed()){
                    ci.cancel();
                }
                if(((StandUser)this.player).roundabout$getStandPowers() instanceof PowersGreenDay PGD){
                    if(!(PGD.HasMainArm && PGD.HasOffHand)){
                        ci.cancel();
                    }
                }
                if(((StandUser)this.player).roundabout$getStandPowers() instanceof PowersCatchTheRainbow PCTR){
                    if(!(PCTR.HasOffHand)){
                        ci.cancel();
                    }
                }

        }
    }


}
