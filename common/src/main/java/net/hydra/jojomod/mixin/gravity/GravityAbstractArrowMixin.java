package net.hydra.jojomod.mixin.gravity;

import net.hydra.jojomod.util.gravity.GravityAPI;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = AbstractArrow.class, priority = 103)
public abstract class GravityAbstractArrowMixin extends Entity implements TraceableEntity {
    public GravityAbstractArrowMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;applyGravity()V"))
    private void roundabout$applyGravity(AbstractArrow arrow) {
        double acceleration = this.getGravity();
        if (acceleration != 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().add(RotationUtil.vecPlayerToWorld(
                    0.0D, -acceleration, 0.0D, GravityAPI.getGravityDirection(this))));
        }
    }

    @Inject(
            method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At(
                    value = "RETURN"
            )
    )
    private void rdbt$init(
            EntityType $$0, LivingEntity $$1, Level $$2, ItemStack ammo, ItemStack weapon, CallbackInfo ci
    ) {
        Direction gravityDirection = GravityAPI.getGravityDirection($$1);
        if (gravityDirection == Direction.DOWN) return;

        Vec3 pos = $$1.getEyePosition().subtract(RotationUtil.vecPlayerToWorld(0.0D, 0.10000000149011612D, 0.0D, gravityDirection));

        this.setPos(pos.x,pos.y,pos.z);
    }
}
