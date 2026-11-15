package net.hydra.jojomod.mixin.items;

import net.hydra.jojomod.item.FancyLighterItem;
import net.hydra.jojomod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ItemEntity.class)
public abstract class FancyLighterItemEntity extends Entity{

    @Inject(method = "tick", at = @At(value = "HEAD"))
    protected void roundabout$tick(CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            ItemStack stack = getItem();
            if (stack != null && !stack.isEmpty() && stack.is(ModItems.FANCY_LIGHTER.asItem())
                    && stack.getCount() > 0) {
                if (this.isInWaterOrRain()) {
                    rdbt$transformLighter(stack);
                } else {
                    AABB box = this.getBoundingBox();
                    if (BlockPos.betweenClosedStream(
                            Mth.floor(box.minX), Mth.floor(box.minY),
                            Mth.floor(box.minZ), Mth.floor(box.maxX),
                            Mth.floor(box.maxY), Mth.floor(box.maxZ)
                    ).anyMatch(blockPos -> rdbt$isWaterCauldron2(level().getBlockState(blockPos))
                            && rdbt$isInsideCauldron2(blockPos))) {
                        rdbt$transformLighter(stack);
                    }
                }
            }
        }
    }

    private boolean rdbt$isWaterCauldron2(BlockState state) {
        return state.is(Blocks.WATER_CAULDRON) && state.getValue(LayeredCauldronBlock.LEVEL) > 0;
    }

    private boolean rdbt$isInsideCauldron2(BlockPos blockPos) {
        double y = getY();
        double x = getX() - blockPos.getX();
        double z = getZ() - blockPos.getZ();

        boolean insideXZ = x > 0.0625 && x < 0.9375 && z > 0.0625 && z < 0.9375;
        boolean insideY = y > blockPos.getY() + 0.25 && y < blockPos.getY() + 0.9375;
        return insideXZ && insideY;
    }

    @Unique
    public void rdbt$transformLighter(ItemStack stack) {
        if (stack.getItem() instanceof FancyLighterItem lighter
                && lighter.getCurrentPredicateValue(this.level(), stack) <= 0.0F) {
            ItemStack unlit = stack.copy();
            lighter.unlit(unlit);
            // Publish a new stack so its component change is synchronized. Keeping the
            // ItemEntity preserves its thrower, pickup target, age, delay and movement.
            setItem(unlit);
        }
    }

    public FancyLighterItemEntity(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Shadow public abstract ItemStack getItem();

    @Shadow public abstract void setItem(ItemStack stack);
}
