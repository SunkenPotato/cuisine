package com.sunkenpotato.cuisine.block;

import com.sunkenpotato.cuisine.Cuisine;
import com.sunkenpotato.cuisine.recipe.PotRecipe;
import com.sunkenpotato.cuisine.recipe.PotRecipeInput;
import com.sunkenpotato.cuisine.sound.CuisineSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Optional;

public class PotBlockEntity extends BlockEntity {

    private final int maxSize = 7;
    DefaultedList<ItemStack> inventory = DefaultedList.ofSize(maxSize, ItemStack.EMPTY);


    public PotBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.POT_BLOCK_ENTITY_T, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapper) {
        NbtList nbtList = new NbtList();
        for (ItemStack stack : inventory) {
            ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, stack).result().ifPresent(nbtList::add);
        }

        nbt.put("Inventory", nbtList);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapper) {
        NbtList nbtInventory = nbt.getList("Inventory", NbtElement.COMPOUND_TYPE);

        for (int i = 0; i < nbtInventory.size(); i++) {
            ItemStack.CODEC.decode(NbtOps.INSTANCE, nbtInventory.getCompound(i)).result().ifPresent(itemStack -> inventory.set(0, itemStack.getFirst()));
        }

    }

    public void dropInventory(World world, BlockPos pos) {
        for (var stack : inventory) {
            if (!stack.isEmpty()) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    public boolean tryAddElement(ItemStack stack) {
        if (isDefaultedListFull(inventory)) {
            Cuisine.LOGGER.info("INVENTORY FULL");
            return false;
        }

        ItemStack copy = new ItemStack(stack.getItem(), 1);

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isEmpty()) {
                inventory.set(i, copy);
                break;
            }
        }

        if (hasRecipe()) craftItem(world, pos);

        return true;
    }

    public void craftItem(World world, BlockPos pos) {
        Optional<RecipeEntry<PotRecipe>> recipe = getCurrentRecipe();

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        world.playSound(null, x, y, z, CuisineSounds.FINISH_COOKING, SoundCategory.MUSIC, 1f, 1f);

        dropItem(world, pos, recipe.get().value().output);
    }

    private void dropItem(World world, BlockPos pos, ItemStack stack) {
        if (!stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<PotRecipe>> recipe = getCurrentRecipe();

        Cuisine.LOGGER.info("Has recipe: {}", recipe.isPresent());

        return recipe.isPresent();
    }

    private Optional<RecipeEntry<PotRecipe>> getCurrentRecipe() {
        PotRecipeInput PRI;
        DefaultedList<Ingredient> tempList = DefaultedList.ofSize(inventory.size(), Ingredient.EMPTY);
        for (int i = 0; i < inventory.size(); i++) {
            tempList.set(i, Ingredient.ofStacks(inventory.get(i)));
        }

         PRI = new PotRecipeInput(tempList);


        Cuisine.LOGGER.info("Possible entries: {}", getWorld().getRecipeManager().listAllOfType(    PotRecipe.PotRecipeType.INSTANCE));

        return getWorld().getRecipeManager().getFirstMatch(PotRecipe.PotRecipeType.INSTANCE, PRI, getWorld());
    }

    private boolean isDefaultedListEmpty(DefaultedList<ItemStack> defaultedList) {
        int k = 0;
        for (ItemStack stack : defaultedList) if (stack.isEmpty()) k++;

        return k == defaultedList.size();
    }

    private boolean isDefaultedListFull(DefaultedList<ItemStack> defaultedList) {
        int k = 0;
        for (ItemStack stack : defaultedList) if (stack.isEmpty()) k++;

        return k == 0;
    }

    public ActionResult tryEmptyInventory(PlayerEntity player) {
        int k = 0;
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) k++;
        }

        if (k == maxSize) {
            return ActionResult.PASS;
        } else {
            emptyInventory(player);
            return ActionResult.SUCCESS;
        }
    }

    private void emptyInventory(PlayerEntity player) {
        for (ItemStack stack : inventory) {
            stack = ItemStack.EMPTY;
            player.giveItemStack(stack);
        }
    }


}
