package net.hydra.jojomod.platform.services;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class FabricPlatformHelperClient implements IPlatformHelperClient {

    public<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> net.minecraft.client.model.Model getArmorModelHook
            (T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
        return null;
    }



}
