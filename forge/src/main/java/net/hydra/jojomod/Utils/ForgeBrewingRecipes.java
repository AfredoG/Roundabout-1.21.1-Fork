package net.hydra.jojomod.Utils;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.hydra.jojomod.util.PotionData;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;

public class ForgeBrewingRecipes implements IBrewingRecipe {
    public static void register(net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();
        builder.addRecipe(new ForgeBrewingRecipes(net.minecraft.world.item.alchemy.Potions.AWKWARD,
                net.hydra.jojomod.registry.ForgeItems.LOCACACA_PIT.get(), net.hydra.jojomod.registry.ForgeItems.HEX_POTION));
        builder.addRecipe(new ForgeBrewingRecipes(net.hydra.jojomod.registry.ForgeItems.HEX_POTION,
                net.minecraft.world.item.Items.REDSTONE, net.hydra.jojomod.registry.ForgeItems.HEX_POTION_EXTENDED));
        builder.addRecipe(new ForgeBrewingRecipes(net.hydra.jojomod.registry.ForgeItems.HEX_POTION,
                net.minecraft.world.item.Items.GLOWSTONE_DUST, net.hydra.jojomod.registry.ForgeItems.HEX_POTION_STRONG));
    }

    private final Holder<Potion> input;
    private final Item ingredient;
    private final Holder<Potion> output;

    public ForgeBrewingRecipes(Holder<Potion> input, Item ingredient, Holder<Potion> output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return PotionData.isPotion(input, this.input);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == this.ingredient;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if(!this.isInput(input) || !this.isIngredient(ingredient)) {
            return ItemStack.EMPTY;
        }

        ItemStack itemStack = new ItemStack(input.getItem());
        PotionData.setPotion(itemStack, this.output);
        return itemStack;
    }
}
