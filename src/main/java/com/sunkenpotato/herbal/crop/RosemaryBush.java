package com.sunkenpotato.herbal.crop;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int age = state.get(AGE);
        boolean mature = (age == 3);

        if (!mature) {
            return ActionResult.PASS;
        }
        int randomStackCount = 1 + world.random.nextInt(2);
        dropStack(world, pos, new ItemStack(CropRegistry.ROSEMARY, randomStackCount + 1));

        world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);

        BlockState blockState = state.with(AGE, 0);
        world.setBlockState(pos, blockState, 2);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));

        return ActionResult.success(world.isClient);
    }



}
