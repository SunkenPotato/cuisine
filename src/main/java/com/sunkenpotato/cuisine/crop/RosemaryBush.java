package com.sunkenpotato.cuisine.crop;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class RosemaryBush extends SweetBerryBushBlock implements Fertilizable {

    public RosemaryBush(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState bs = world.getBlockState(pos.down());
        return bs.isOf(Blocks.FARMLAND);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(Blocks.FARMLAND);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE)
            entity.slowMovement(state, new Vec3d(0.800000011920929, 0.75, 0.800000011920929));
    }

    @Override
    public ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        int age = state.get(AGE);
        boolean mature = (age == MAX_AGE);

        if (!mature) return ItemActionResult.FAIL;
        if (!stack.isOf(Items.SHEARS)) return ItemActionResult.FAIL;

        int randomStackCount = 1 + world.random.nextInt(2);
        dropStack(world, pos, new ItemStack(CropRegistry.ROSEMARY, randomStackCount + 1));

        world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);

        BlockState blockState = state.with(AGE, 2);
        world.setBlockState(pos, blockState, 2);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));

        stack.setDamage(1);

        return ItemActionResult.SUCCESS;
    }


}
