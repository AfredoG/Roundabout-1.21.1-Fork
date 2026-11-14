package net.hydra.jojomod.platform.services;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public interface IPlatformHelperClient {
    default ResourceLocation getArmorTexture(Entity entity, ItemStack armor, ArmorMaterial.Layer layer,
                                             boolean innerModel, EquipmentSlot slot) {
        return layer.texture(innerModel);
    }

    default int getDefaultArmorDyeColor(ItemStack stack) {
        return stack.is(ItemTags.DYEABLE)
                ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR))
                : 0xFFFFFFFF;
    }

    default int getArmorLayerTintColor(ItemStack stack, LivingEntity entity, ArmorMaterial.Layer layer,
                                       int layerIndex, int fallbackColor) {
        return layer.dyeable() ? fallbackColor : 0xFFFFFFFF;
    }

    default void setupArmorModelAnimations(LivingEntity entity, ItemStack stack, EquipmentSlot slot, Model model,
                                           float limbSwing, float limbSwingAmount, float partialTick,
                                           float ageInTicks, float netHeadYaw, float headPitch) {}

    <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> Model getArmorModelHook
            (T entity, ItemStack itemStack, EquipmentSlot slot, A model);
}
