package net.hydra.jojomod.item;

import net.hydra.jojomod.entity.projectile.StandArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RoundaboutArrowItem extends ArrowItem {

    public RoundaboutArrowItem(Properties $$0) {
        super($$0);
    }

    @Override
    public AbstractArrow createArrow(Level $$0, ItemStack $$1, LivingEntity $$2, @javax.annotation.Nullable ItemStack weapon) {
        return new StandArrowEntity($$0, $$2, $$1, weapon);
    }
}
