package net.hydra.jojomod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EquippableStoneMaskBlock extends StoneMaskBlock implements Equipable {
    public static final MapCodec<EquippableStoneMaskBlock> CODEC = simpleCodec(EquippableStoneMaskBlock::new);

    @Override
    public MapCodec<? extends EquippableStoneMaskBlock> codec() {
        return CODEC;
    }

    protected EquippableStoneMaskBlock(BlockBehaviour.Properties $$0) {
        super($$0);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
