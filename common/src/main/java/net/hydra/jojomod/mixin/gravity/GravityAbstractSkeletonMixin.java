package net.hydra.jojomod.mixin.gravity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hydra.jojomod.util.gravity.GravityAPI;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSkeleton.class)
public abstract class GravityAbstractSkeletonMixin {
    // Adjust only aim: retain native weapon selection, enchanted/custom arrows and spawning.
    @WrapOperation(method = "performRangedAttack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;shoot(DDDFF)V"))
    private void roundabout$aimAtRotatedTarget(AbstractArrow arrow, double x, double y, double z,
                                              float speed, float spread, Operation<Void> original,
                                              LivingEntity target, float power) {
        Direction gravity = GravityAPI.getGravityDirection(target);
        if (gravity != Direction.DOWN) {
            AbstractSkeleton shooter = (AbstractSkeleton) (Object) this;
            Vec3 aim = target.position().add(RotationUtil.vecPlayerToWorld(
                    0.0, target.getBbHeight() / 3.0, 0.0, gravity));
            x = aim.x - shooter.getX();
            z = aim.z - shooter.getZ();
            y = aim.y - arrow.getY() + Math.sqrt(x * x + z * z) * 0.2F;
        }
        original.call(arrow, x, y, z, speed, spread);
    }
}
