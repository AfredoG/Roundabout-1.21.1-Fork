package net.hydra.jojomod.block;

import com.mojang.serialization.MapCodec;
import net.hydra.jojomod.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ChamomilePlantBlock
        extends PlantBlock {
    public static final MapCodec<ChamomilePlantBlock> CODEC = simpleCodec(ChamomilePlantBlock::new);

    @Override
    public MapCodec<? extends ChamomilePlantBlock> codec() {
        return CODEC;
    }


    public ChamomilePlantBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }


    @Override
    public Item getDrop() {
        return ModItems.CHAMOMILE;
    }
    @Override
    public Item getPlant() {return ModItems.CHAMOMILE_SEEDS;}

}
