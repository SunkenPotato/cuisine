package com.sunkenpotato.cuisine.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class InteractableCookingBlockEntity {

     public final int maxSize = getMaxSize();
     public boolean isCooking = false;
     private final int maxCookingTime = getMaxCookingTime();
     private final int cookingDelta = 0;
     private final List<Float> randomValues = getRandomValues();
     public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(maxSize, ItemStack.EMPTY);


    /**
     * Supply a way to access the inventory
     */
    abstract DefaultedList<ItemStack> getInventory();

    abstract List<Float> getRandomValues();

    abstract int getMaxSize();

    final boolean isCooking() {
        return isCooking;
    }

    abstract int getMaxCookingTime();

    abstract void dropInventory(World world, BlockPos pos);

    abstract boolean tryAddElement(ItemStack stack, BlockState state, World world, BlockPos pos);

}
