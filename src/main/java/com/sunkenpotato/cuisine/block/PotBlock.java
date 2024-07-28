package com.sunkenpotato.cuisine.block;

import com.mojang.serialization.MapCodec;
import com.sunkenpotato.cuisine.Cuisine;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PotBlock extends BlockWithEntity implements BlockEntityProvider {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.d, .0d, 2.d, 14.d, 7.d, 14.d);


    public PotBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PotBlock::new);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PotBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(view, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        PotBlockEntity blockEntityAtLocation = (PotBlockEntity) world.getBlockEntity(pos);

        if (blockEntityAtLocation.tryAddElement(stack)) {
            stack.decrementUnlessCreative(1, player);
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.FAIL;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);

        PotBlockEntity blockEntity = ((PotBlockEntity) world.getBlockEntity(pos));

        if (blockEntity != null) {
            blockEntity.dropInventory(world, pos);
        }

        return state;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        PotBlockEntity pbe = ((PotBlockEntity) world.getBlockEntity(pos));

        if (pbe == null) return ActionResult.FAIL;

        Cuisine.LOGGER.info("Trying to empty inventory...");

        return pbe.tryEmptyInventory(player);
    }
}
