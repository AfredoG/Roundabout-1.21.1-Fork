package net.hydra.jojomod.mixin.gravity;

import net.hydra.jojomod.util.gravity.GravityAPI;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@SuppressWarnings("resource")
@Mixin(value = Player.class, priority = 1001)
public abstract class GravityPlayerMixin extends LivingEntity {
    protected GravityPlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    public Player rdbt$this(){
        return ((Player)(Object)this);
    }

    @com.llamalad7.mixinextras.injector.ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getLookAngle()Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 roundabout$localSwimmingLook(Vec3 look) {
        return RotationUtil.vecWorldToPlayer(look, GravityAPI.getGravityDirection(this));
    }

    @org.spongepowered.asm.mixin.injection.ModifyArgs(method = "travel", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/core/BlockPos;containing(DDD)Lnet/minecraft/core/BlockPos;"))
    private void roundabout$swimmingFluidProbe(org.spongepowered.asm.mixin.injection.invoke.arg.Args args) {
        Direction gravity = GravityAPI.getGravityDirection(this);
        if (gravity != Direction.DOWN) {
            Vec3 local = new Vec3((double)args.get(0), (double)args.get(1), (double)args.get(2)).subtract(position());
            Vec3 world = position().add(RotationUtil.vecPlayerToWorld(local, gravity));
            args.setAll(world.x, world.y, world.z);
        }
    }

    @Inject(
            method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void roundabout$dropGravity(
            ItemStack $$0, boolean $$1, boolean $$2, CallbackInfoReturnable<ItemEntity> cir
    ) {
        Direction gravityDirection = GravityAPI.getGravityDirection((Entity) (Object) this);
        if (gravityDirection == Direction.DOWN)
            return;

        if ($$0.isEmpty()) {
            cir.setReturnValue(null);
        } else {
            if (this.level().isClientSide) {
                this.swing(InteractionHand.MAIN_HAND);
            }

            Vec3 vec3d = this.getEyePosition()
                    .subtract(RotationUtil.vecPlayerToWorld(0.0D, 0.3D, 0.0D, gravityDirection));

            ItemEntity $$4 = new ItemEntity(this.level(), vec3d.x, vec3d.y, vec3d.z, $$0);
            $$4.setPickUpDelay(40);
            if ($$2) {
                $$4.setThrower(this);
            }

            if ($$1) {
                float $$5 = this.random.nextFloat() * 0.5F;
                float $$6 = this.random.nextFloat() * (float) (Math.PI * 2);

                Vec3 world = RotationUtil.vecPlayerToWorld((double)(-Mth.sin($$6) * $$5), 0.2F, (double)(Mth.cos($$6) * $$5), gravityDirection);
                GravityAPI.setWorldVelocity($$4, world);
            } else {
                float $$7 = 0.3F;
                float $$8 = Mth.sin(this.getXRot() * (float) (Math.PI / 180.0));
                float $$9 = Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
                float $$10 = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0));
                float $$11 = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
                float $$12 = this.random.nextFloat() * (float) (Math.PI * 2);
                float $$13 = 0.02F * this.random.nextFloat();
                Vec3 world = RotationUtil.vecPlayerToWorld((double)(-$$10 * $$9 * 0.3F) + Math.cos((double)$$12) * (double)$$13,
                        (double)(-$$8 * 0.3F + 0.1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F),
                        (double)($$11 * $$9 * 0.3F) + Math.sin((double)$$12) * (double)$$13, gravityDirection);
                GravityAPI.setWorldVelocity($$4, world);
            }

            cir.setReturnValue($$4);
        }
    }

    @com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod(method = "maybeBackOffFromEdge")
    private Vec3 roundabout$localEdgeMovement(Vec3 movement, MoverType type,
            com.llamalad7.mixinextras.injector.wrapoperation.Operation<Vec3> original) {
        Direction gravity = GravityAPI.getGravityDirection(this);
        if (gravity == Direction.DOWN) return original.call(movement, type);
        Vec3 local = RotationUtil.vecWorldToPlayer(movement, gravity);
        return RotationUtil.vecPlayerToWorld(original.call(local, type), gravity);
    }

    @Inject(method = "canFallAtLeast", at = @At("HEAD"), cancellable = true)
    private void roundabout$rotatedSupportProbe(double x, double z, float distance, CallbackInfoReturnable<Boolean> cir) {
        Direction gravity = GravityAPI.getGravityDirection(this);
        if (gravity == Direction.DOWN) return;
        net.minecraft.world.phys.AABB local = RotationUtil.boxWorldToPlayer(getBoundingBox().move(position().reverse()), gravity);
        net.minecraft.world.phys.AABB below = new net.minecraft.world.phys.AABB(
                local.minX + x, local.minY - distance - 1.0E-5F, local.minZ + z,
                local.maxX + x, local.minY, local.maxZ + z);
        cir.setReturnValue(level().noCollision(this, RotationUtil.boxPlayerToWorld(below, gravity).move(position())));
    }

    @Unique
    private float roundabout$tempStoreYRot = 0;
    @Unique
    private boolean roundabout$tempStoreYRotShifted = false;

    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"
            )
    )
    private void roundabout$attackY1(Entity target, CallbackInfo ci) {
        Direction targetGravityDirection = GravityAPI.getGravityDirection(target);
        Direction attackerGravityDirection = GravityAPI.getGravityDirection(this);
        if (targetGravityDirection == attackerGravityDirection)
            return;

        roundabout$tempStoreYRot = getYRot();
        roundabout$tempStoreYRotShifted = true;
        setYRot(RotationUtil.rotWorldToPlayer(RotationUtil.rotPlayerToWorld(getYRot(), getXRot(), attackerGravityDirection), targetGravityDirection).x);
    }
    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V",
                    shift = At.Shift.AFTER
            )
    )
    private void roundabout$attackY2(Entity target, CallbackInfo ci) {
        if (roundabout$tempStoreYRotShifted){
            roundabout$tempStoreYRotShifted = false;
            setYRot(roundabout$tempStoreYRot);
        }
    }


    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;push(DDD)V"
            )
    )
    private void roundabout$attackY1push(Entity target, CallbackInfo ci) {
        Direction gravityDirection = GravityAPI.getGravityDirection(this);
        if (gravityDirection == Direction.DOWN)
            return;

        roundabout$tempStoreYRot = getYRot();
        roundabout$tempStoreYRotShifted = true;
        setYRot(RotationUtil.rotPlayerToWorld(getYRot(), getXRot(), gravityDirection).x);
    }
    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;push(DDD)V",
                    shift = At.Shift.AFTER
            )
    )
    private void roundabout$attackY2push(Entity target, CallbackInfo ci) {
        if (roundabout$tempStoreYRotShifted){
            roundabout$tempStoreYRotShifted = false;
            setYRot(roundabout$tempStoreYRot);
        }
    }

}
