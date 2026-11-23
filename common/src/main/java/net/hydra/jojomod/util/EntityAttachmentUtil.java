package net.hydra.jojomod.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EntityAttachments;
import net.minecraft.world.phys.Vec3;

/** Keeps nameplate changes independent of passenger and vehicle attachments. */
public final class EntityAttachmentUtil {
    private EntityAttachmentUtil() {}

    public static EntityAttachments withNameTagHeight(EntityAttachments original, float width, float height, double y) {
        EntityAttachments.Builder builder = EntityAttachments.builder();
        for (EntityAttachment type : EntityAttachment.values()) {
            for (int i = 0; ; i++) {
                Vec3 point = original.getNullable(type, i, 0.0F);
                if (point == null) break;
                builder.attach(type, type == EntityAttachment.NAME_TAG && i == 0 ? new Vec3(point.x, y, point.z) : point);
            }
        }
        return builder.build(width, height);
    }

    public static Vec3 nameTagPosition(Entity entity, float partialTick) {
        Vec3 point = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getViewYRot(partialTick));
        return point == null ? new Vec3(0.0, entity.getBbHeight() + 0.5, 0.0) : point.add(0.0, 0.5, 0.0);
    }
}
