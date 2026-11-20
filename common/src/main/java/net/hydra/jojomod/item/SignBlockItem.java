package net.hydra.jojomod.item;

import net.hydra.jojomod.util.ItemCombatData;
import net.hydra.jojomod.event.ModEffects;
import net.hydra.jojomod.event.powers.StandUser;
import net.hydra.jojomod.sound.ModSounds;
import net.hydra.jojomod.util.MainUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class SignBlockItem extends BlockItem {
    public SignBlockItem(Block block, Item.Properties properties) {
        super(block, properties.attributes(ItemCombatData.weaponAttributes(7.0, -2.7)));
    }


    public float getDamage() {
        return 9.2F;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
