package com.sunkenpotato.cuisine.block;

import com.sunkenpotato.cuisine.Cuisine;
import com.sunkenpotato.cuisine.recipe.Base;
import com.sunkenpotato.cuisine.recipe.PotRecipe;
import com.sunkenpotato.cuisine.recipe.PotRecipeInput;
import com.sunkenpotato.cuisine.sound.CuisineSounds;
import com.sunkenpotato.cuisine.util.BaseUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.command.StopCommand;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * BlockEntity for Pot
 * //TODO// cleanup
 * @see com.sunkenpotato.cuisine.block.PotBlock
 */
@SuppressWarnings("DataFlowIssue")
public class PotBlockEntity extends BlockEntity {

    public final Random random;
    public final List<Float> randomValues = new ArrayList<>();

    private final int maxSize = 7;
    public final int maxCookingTime = 4 * 20;
    public int cookingDelta = 0;

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(maxSize, ItemStack.EMPTY);
    public IntProperty stateContents = PotBlock.CONTENTS;
    public Base base = Base.NULL;
    private boolean isCooking = false;

    public PotBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.POT_BLOCK_ENTITY_T, pos, state);
        random = new Random(pos.hashCode());

        // Initialize randomValues for the BlockEntityRenderer
        int k = 0;
        while (k <= maxSize) {
            randomValues.add(random.nextFloat(0, 1));
            k++;
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapper) {
        Inventories.writeNbt(nbt, this.inventory, wrapper);
        nbt.putString("Base", base.name());

        super.writeNbt(nbt, wrapper);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapper) {
        super.readNbt(nbt, wrapper);
        this.inventory = DefaultedList.ofSize(maxSize, ItemStack.EMPTY);
        base = Base.valueOf(nbt.getString("Base").toUpperCase());
        Inventories.readNbt(nbt, this.inventory, wrapper);

    }

    public void dropInventory(World world, BlockPos pos) {
        for (var stack : inventory) {
            if (!stack.isEmpty()) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    public boolean tryAddElement(ItemStack stack, BlockState state, World world, BlockPos pos) {
        if (isDefaultedListFull(inventory)) {
            return false;
        }

        ItemStack copy = new ItemStack(stack.getItem(), 1);

        boolean isOfBase = BaseUtil.isItemOfBase(copy);

        if (base == Base.NULL && isOfBase) {
            base = BaseUtil.getBaseFromItem(copy);

            BlockState newState = state.with(stateContents, BaseUtil.baseToInt(base));
            world.setBlockState(pos, newState);


            return true;
        }

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isEmpty()) {
                inventory.set(i, copy);
                break;
            }
        }

        return true;
    }

    public void beginCookingProcess() {
        isCooking = true;
    }

    public void endCookingProcess() {
        isCooking = false;
        cookingDelta = 0;
    }

    public boolean isCooking() {
        return isCooking;
    }

    private boolean isHeated() {
        return getBlockUnderneath() instanceof CampfireBlock;
    }

    private Block getBlockUnderneath() {
        BlockPos pos = new BlockPos(getPos().getX(), getPos().getY() - 1, getPos().getZ());
        Cuisine.LOGGER.info("{}", pos);
        return world.getBlockState(pos).getBlock();
    }

    public void craftItem(World world, BlockPos pos) {
        Optional<RecipeEntry<PotRecipe>> recipe = getCurrentRecipe();

        dropItem(world, pos, recipe.get().value().output);

        inventory.clear();
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

        return recipe.isPresent();
    }

    private Optional<RecipeEntry<PotRecipe>> getCurrentRecipe() {
        PotRecipeInput PRI;
        DefaultedList<ItemStack> tempList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < inventory.size(); i++) {
            tempList.set(i, inventory.get(i));
        }

        PRI = new PotRecipeInput(tempList, base);

        return getWorld().getRecipeManager().getFirstMatch(PotRecipe.PotRecipeType.INSTANCE, PRI, getWorld());
    }

    private boolean isDefaultedListFull(DefaultedList<ItemStack> defaultedList) {
        int k = 0;
        for (ItemStack stack : defaultedList) if (stack.isEmpty()) k++;

        return k == 0;
    }

    public ItemActionResult tryEmptyInventory() {
        int k = 0;
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) k++;
        }

        if (k == maxSize) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            emptyInventory();
            return ItemActionResult.SUCCESS;
        }
    }

    private void emptyInventory() {
        for (ItemStack stack : inventory) {
            dropItem(world, pos, stack);
        }
        inventory.clear();
    }

    public void updateState(BlockState state) {
        switch (state.get(stateContents)) {
            case 0 -> base = Base.WATER;
            case 1 -> base = Base.OIL;
            case 2 -> base = Base.NULL;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, PotBlockEntity blockEntity) {

        if (blockEntity.hasRecipe() && blockEntity.isHeated()) {
            blockEntity.beginCookingProcess();
        }

        if (blockEntity.isCooking()) {
            if (blockEntity.cookingDelta == blockEntity.maxCookingTime) {
                blockEntity.endCookingProcess();
                blockEntity.craftItem(world, pos);
            } else if (blockEntity.cookingDelta == 0) {
                world.playSound(null, pos, CuisineSounds.FINISH_COOKING, SoundCategory.MASTER, 1f, 1f);
                blockEntity.cookingDelta++;
            }
            else {
                blockEntity.cookingDelta ++;
            }
        }
    }


}
