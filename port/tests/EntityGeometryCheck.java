package net.hydra.jojomod.entity.paintings;

import java.util.List;
import java.util.Map;
import net.hydra.jojomod.util.EntityAttachmentUtil;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EntityAttachments;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Uses the production painting class and attachment helper, without a game world. */
public final class EntityGeometryCheck {
    private static int checks;

    private static void close(double actual, double expected) {
        if (Math.abs(actual - expected) > 0.000001) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
        checks++;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final class TestPainting extends RoundaboutPainting {
        private final int width;
        private final int height;

        TestPainting(int width, int height, BlockPos pos, Direction facing) {
            super((EntityType) EntityType.PAINTING, null, pos);
            this.width = width;
            this.height = height;
            setDirection(facing);
        }

        @Override public int getWidth() { return width; }
        @Override public int getHeight() { return height; }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void main(String[] args) {
        net.neoforged.fml.loading.LoadingModList.of(List.of(), List.of(), List.of(), List.of(), Map.of());
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        // Entity construction needs the NeoForge empty-fluid holder even without a level.
        ((net.minecraft.core.MappedRegistry<?>) net.minecraft.core.registries.BuiltInRegistries.REGISTRY).unfreeze();
        net.minecraft.core.Registry.register((net.minecraft.core.Registry) net.minecraft.core.registries.BuiltInRegistries.REGISTRY,
                net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.FLUID_TYPES.location(),
                net.neoforged.neoforge.registries.NeoForgeRegistries.FLUID_TYPES);
        net.minecraft.core.Registry.register(net.neoforged.neoforge.registries.NeoForgeRegistries.FLUID_TYPES,
                net.minecraft.resources.ResourceLocation.withDefaultNamespace("empty"),
                new net.neoforged.neoforge.fluids.FluidType(net.neoforged.neoforge.fluids.FluidType.Properties.create()) {});
        BlockPos origin = new BlockPos(10, 64, -20);
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (int[] size : new int[][]{{16, 32}, {64, 48}, {32, 16}}) {
                TestPainting painting = new TestPainting(size[0], size[1], origin, facing);
                AABB box = painting.getBoundingBox();
                close(box.getXsize(), facing.getAxis() == Direction.Axis.X ? 0.0625 : size[0] / 16.0);
                close(box.getYsize(), size[1] / 16.0);
                close(box.getZsize(), facing.getAxis() == Direction.Axis.Z ? 0.0625 : size[0] / 16.0);
                Vec3 center = box.getCenter();
                // The surface sits 1/16 of a block in front of the support wall.
                double normalCenter = facing.getAxis() == Direction.Axis.X ? center.x - origin.getX() : center.z - origin.getZ();
                close(normalCenter, facing.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 0.03125 : 0.96875);
                close(center.y, origin.getY() + (size[1] % 32 == 0 ? 1.0 : 0.5));
                double tangentCenter = facing.getAxis() == Direction.Axis.X ? center.z - origin.getZ() : center.x - origin.getX();
                int tangentSign = facing.getCounterClockWise().getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1 : -1;
                close(tangentCenter, 0.5 + (size[0] % 32 == 0 ? tangentSign * 0.5 : 0.0));
                ClientboundAddEntityPacket packet = (ClientboundAddEntityPacket) painting.getAddEntityPacket(null);
                close(packet.getX(), origin.getX());
                close(packet.getY(), origin.getY());
                close(packet.getZ(), origin.getZ());
                close(packet.getData(), facing.get3DDataValue());
            }
        }

        EntityAttachments attachments = EntityAttachments.builder()
                .attach(EntityAttachment.PASSENGER, 0.2F, 1.1F, 0.4F)
                .attach(EntityAttachment.PASSENGER, -0.6F, 1.2F, -0.3F)
                .attach(EntityAttachment.VEHICLE, 0.0F, 0.35F, 0.0F)
                .attach(EntityAttachment.NAME_TAG, 0.1F, 1.8F, 0.2F).build(0.6F, 1.8F);
        EntityAttachments changed = EntityAttachmentUtil.withNameTagHeight(attachments, 0.6F, 1.8F, 2.05);
        for (EntityAttachment type : EntityAttachment.values()) {
            for (int i = 0; ; i++) {
                Vec3 original = attachments.getNullable(type, i, 0.0F);
                if (original == null) break;
                Vec3 actual = changed.get(type, i, 0.0F);
                close(actual.x, original.x);
                close(actual.z, original.z);
                close(actual.y, type == EntityAttachment.NAME_TAG ? 2.05 : original.y);
            }
        }
        close(attachments.get(EntityAttachment.NAME_TAG, 0, 0).y, 1.8F);
        System.out.println("PASS: " + checks + " entity geometry assertions");
    }
}
