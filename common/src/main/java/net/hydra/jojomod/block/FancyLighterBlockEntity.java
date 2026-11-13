package net.hydra.jojomod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class FancyLighterBlockEntity extends BlockEntity {
    public FancyLighterBlockEntity(BlockPos $$1, BlockState $$2) {
        super(ModBlocks.FANCY_LIGHTER_BLOCK_ENTITY, $$1, $$2);
    }

    /*This is the integer in which the owner's id gets saved*/
    private UUID owner;

    public UUID getOwner(){
        return owner;
    }


    public void setValue(UUID thisowner){
        if(owner == null){
            owner = thisowner;
        }
    }
    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        if(owner != null) {
            tag.putUUID("owner", owner);
        }

        super.saveAdditional(tag, registries);
    }

    @Override
    public void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if(tag.contains("owner")) {
            owner = tag.getUUID("owner");
        }
    }

    public void transferUUID(){
        if (this.getBlockState() != null){
            System.out.println(getOwner());
        }
    }



}
