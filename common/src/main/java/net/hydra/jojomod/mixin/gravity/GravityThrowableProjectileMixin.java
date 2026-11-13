package net.hydra.jojomod.mixin.gravity;

import net.hydra.jojomod.access.AccessThrowableProjectile;
import net.hydra.jojomod.util.gravity.GravityAPI;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrowableProjectile.class)
public abstract class GravityThrowableProjectileMixin extends Entity implements AccessThrowableProjectile {

    public GravityThrowableProjectileMixin(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }


    @Unique
    @Override
    public float rdbt$getGravity(){
        return (float) getGravity();
    }
    /*@Override
    public Direction gravitychanger$getAppliedGravityDirection() {
        return GravityChangerAPI.getGravityDirection((ThrownEntity)(Object)this);
    }*/

    @org.spongepowered.asm.mixin.injection.Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ThrowableProjectile;applyGravity()V")
    )
    private void roundabout$applyDirectionalGravity(ThrowableProjectile projectile) {
        Vec3 acceleration = RotationUtil.vecPlayerToWorld(0.0D, -projectile.getGravity(), 0.0D,
                GravityAPI.getGravityDirection(projectile));
        projectile.setDeltaMovement(projectile.getDeltaMovement().add(acceleration));
    }

    @Inject(
            method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;)V",
            at = @At(
                    value = "RETURN"
            )
    )
    private void rdbt$init(EntityType $$0, LivingEntity $$1, Level $$2, CallbackInfo ci) {
        Direction gravityDirection = GravityAPI.getGravityDirection($$1);
        if (gravityDirection == Direction.DOWN) return;

        Vec3 pos = $$1.getEyePosition().subtract(RotationUtil.vecPlayerToWorld(0.0D, 0.10000000149011612D, 0.0D, gravityDirection));
        this.setPos(pos.x, pos.y, pos.z);
    }
}