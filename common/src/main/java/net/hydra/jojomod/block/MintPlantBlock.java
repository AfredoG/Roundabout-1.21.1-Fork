package net.hydra.jojomod.block;

import com.mojang.serialization.MapCodec;
import net.hydra.jojomod.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class MintPlantBlock
        extends PlantBlock {
    public static final MapCodec<MintPlantBlock> CODEC = simpleCodec(MintPlantBlock::new);

    @Override
    public MapCodec<? extends MintPlantBlock> codec() {
        return CODEC;
    }


    public MintPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Item getDrop() {
        return ModItems.MINT;
    }
    @Override
    public Item getPlant() {return ModItems.MINT_SEEDS;}
}
