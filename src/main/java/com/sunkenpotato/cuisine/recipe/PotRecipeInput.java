package com.sunkenpotato.cuisine.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.Arrays;

public class PotRecipeInput implements RecipeInput {

    public final DefaultedList<Ingredient> input;
    public final DefaultedList<ItemStack> correspondingStacks = DefaultedList.ofSize(7, ItemStack.EMPTY);

    public PotRecipeInput(DefaultedList<Ingredient> input) {
        this.input = input;

        for (int i = 0; i < input.size(); i++) {
            correspondingStacks.set(i, Arrays.stream(input.get(0).getMatchingStacks()).findFirst().orElse(ItemStack.EMPTY));
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot) {

        return correspondingStacks.get(slot);
    }

    @Override
    public int getSize() {
        return 7;
    }
}
