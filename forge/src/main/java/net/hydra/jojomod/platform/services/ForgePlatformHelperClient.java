package net.hydra.jojomod.platform.services;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ForgePlatformHelperClient implements IPlatformHelperClient {
    @Override
    public <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> Model getArmorModelHook
            (T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
        return ClientHooks.getArmorModel(entity, itemStack, slot, model);
    }

    @Override
    public ResourceLocation getArmorTexture(Entity entity, ItemStack armor, ArmorMaterial.Layer layer,
                                            boolean innerModel, EquipmentSlot slot) {
        return ClientHooks.getArmorTexture(entity, armor, layer, innerModel, slot);
    }

    @Override
    public int getDefaultArmorDyeColor(ItemStack stack) {
        return IClientItemExtensions.of(stack).getDefaultDyeColor(stack);
    }

    @Override
    public int getArmorLayerTintColor(ItemStack stack, LivingEntity entity, ArmorMaterial.Layer layer,
                                      int layerIndex, int fallbackColor) {
        return IClientItemExtensions.of(stack).getArmorLayerTintColor(stack, entity, layer, layerIndex, fallbackColor);
    }

    @Override
    public void setupArmorModelAnimations(LivingEntity entity, ItemStack stack, EquipmentSlot slot, Model model,
                                          float limbSwing, float limbSwingAmount, float partialTick,
                                          float ageInTicks, float netHeadYaw, float headPitch) {
        IClientItemExtensions.of(stack).setupModelAnimations(entity, stack, slot, model,
                limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
    }
}
