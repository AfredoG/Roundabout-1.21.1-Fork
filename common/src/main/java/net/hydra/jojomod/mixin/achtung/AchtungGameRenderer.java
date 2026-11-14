package net.hydra.jojomod.mixin.achtung;

import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.event.index.PowerTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(GameRenderer.class)
public abstract class AchtungGameRenderer {
    @Shadow
    @Final
    private Minecraft minecraft;

    /***
     * Just being extra careful that the transparency achtung baby creates is getting reset in potential
     * spill-over areas in case mods are canceling things. This is the tail end of the renderItemInHand
     * function.
     */
    @Inject(method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V", at = @At(value = "TAIL"))
    private void roundabout$renderItemInHand(CallbackInfo ci)
    {
        ClientUtil.setThrowFadeToTheEther(1);
        ClientUtil.hideInvis = false;
    }


    // Keep vanilla/NeoForge reach attributes, occlusion and hit-result filtering.
    @ModifyArg(
            method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;"),
            index = 4)
    private Predicate<Entity> roundabout$filterDifferentExistence(Predicate<Entity> predicate) {
        if (this.minecraft.player == null) {
            return predicate;
        }
        return predicate.and(entity -> !PowerTypes.isInADifferentExistence(entity, this.minecraft.player));
    }
}
