package com.sunkenpotato.cuisine.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;

public class PotRecipeInput implements RecipeInput {

    public final DefaultedList<ItemStack> input;
    public final Base base;
    private final RecipeMatcher matcher = new RecipeMatcher();

    public PotRecipeInput(DefaultedList<ItemStack> input, Base base) {
        this.input = input;
        this.base = base;

        for (ItemStack itemStack : input) {
            if (!itemStack.isEmpty()) {
                this.matcher.addInput(itemStack, 1);
            }
        }

        this.matcher.addInput(new ItemStack(Items.WATER_BUCKET, 1));

    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return input.get(slot);
    }

    @Override
    public int getSize() {
        return 7;
    }

    public RecipeMatcher getMatcher() {
        return matcher;
    }
}
