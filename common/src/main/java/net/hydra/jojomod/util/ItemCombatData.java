package net.hydra.jojomod.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/** Shared component-based weapon attributes for the 1.21 port. */
public final class ItemCombatData {
    private ItemCombatData() {}

    public static ItemAttributeModifiers weaponAttributes(double damage, double speed) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    /** The weapon contribution, without the wielder's base attack damage or enchantments. */
    public static float attackDamage(ItemStack stack) {
        ItemAttributeModifiers modifiers = stack.getAttributeModifiers();
        ItemAttributeModifiers damage = new ItemAttributeModifiers(modifiers.modifiers().stream()
                .filter(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE)).toList(), false);
        return (float) damage.compute(0.0, EquipmentSlot.MAINHAND);
    }
}
