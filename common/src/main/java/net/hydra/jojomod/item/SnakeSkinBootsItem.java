package net.hydra.jojomod.item;

import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

/** Material factory; the loader registers it before constructing the armor item. */
public final class SnakeSkinBootsItem {
    private SnakeSkinBootsItem() {}
    public static ArmorMaterial createMaterial() {
        return new ArmorMaterial(Map.of(
                ArmorItem.Type.BOOTS, 1, ArmorItem.Type.LEGGINGS, 2,
                ArmorItem.Type.CHESTPLATE, 3, ArmorItem.Type.HELMET, 1),
                9, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ModItems.SNAKE_SKIN),
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("roundabout", "snake_skin_armor"))),
                0.0F, 0.0F);
    }
}
