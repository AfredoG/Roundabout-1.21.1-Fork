package net.hydra.jojomod.util;

import net.minecraft.SharedConstants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/** Standalone regression check against the actual Minecraft/NeoForge classes. */
public final class ItemCombatDataCheck {
    private static int checks;

    private static void damage(ItemStack stack, float expected) {
        float actual = ItemCombatData.attackDamage(stack);
        if (Math.abs(actual - expected) > 0.00001F) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
        checks++;
    }

    public static void main(String[] args) {
        net.neoforged.fml.loading.LoadingModList.of(java.util.List.of(), java.util.List.of(),
                java.util.List.of(), java.util.List.of(), java.util.Map.of());
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        damage(Items.IRON_SWORD.getDefaultInstance(), 5);
        damage(Items.DIAMOND_SWORD.getDefaultInstance(), 6);
        damage(Items.NETHERITE_SWORD.getDefaultInstance(), 7);
        damage(Items.IRON_PICKAXE.getDefaultInstance(), 3);
        damage(Items.DIAMOND_PICKAXE.getDefaultInstance(), 4);
        damage(Items.NETHERITE_PICKAXE.getDefaultInstance(), 5);
        damage(Items.STICK.getDefaultInstance(), 0);
        damage(ItemStack.EMPTY, 0);

        ItemStack stack = Items.IRON_SWORD.getDefaultInstance();
        // Scissors, dagger, harpoon and sign: attack speed must never reduce damage.
        for (double[] values : new double[][]{{2, -1.6F}, {2.5F, -1.9F}, {6, -2.7F}, {7, -2.7}}) {
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemCombatData.weaponAttributes(values[0], values[1]));
            damage(stack, (float) values[0]);
        }
        // An offhand-only damage modifier contributes nothing to a main-hand attack.
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                        50, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND).build());
        damage(stack, 0);
        System.out.println("PASS: " + checks + " weapon attribute cases");
    }
}
