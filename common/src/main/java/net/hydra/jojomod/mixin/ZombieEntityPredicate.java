package net.hydra.jojomod.mixin;

import net.hydra.jojomod.event.index.FateTypes;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** A zombie fate belongs to an individual, so it cannot be expressed by an entity-type tag alone. */
@Mixin(EntityPredicate.class)
public class ZombieEntityPredicate {
    @Redirect(method = "matches(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/EntityTypePredicate;matches(Lnet/minecraft/world/entity/EntityType;)Z"))
    private boolean roundabout$zombieType(EntityTypePredicate predicate, EntityType<?> type,
                                          ServerLevel level, Vec3 origin, Entity entity) {
        return predicate.matches(type) || (entity instanceof LivingEntity living && FateTypes.isZombie(living)
                && predicate.types().unwrapKey().filter(tag -> tag.equals(EntityTypeTags.UNDEAD)
                        || tag.equals(EntityTypeTags.SENSITIVE_TO_SMITE)).isPresent());
    }
}
