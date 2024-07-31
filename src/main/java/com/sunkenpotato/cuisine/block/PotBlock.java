package com.sunkenpotato.cuisine.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Block instance for PotBlockEntity
 * @see com.sunkenpotato.cuisine.block.PotBlockEntity
 */
public class PotBlock extends BlockWithEntity implements BlockEntityProvider {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.d, .0d, 2.d, 14.d, 7.d, 14.d);

    public static final IntProperty CONTENTS = IntProperty.of("contents", 0, 2);


    public PotBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(CONTENTS, 2));
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

    // IntelliJ complaining about things being null
    @SuppressWarnings("DataFlowIssue")
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        PotBlockEntity blockEntityAtLocation = (PotBlockEntity) world.getBlockEntity(pos);

        if (stack.isEmpty()) {
            return blockEntityAtLocation.tryEmptyInventory();
        }

        if (blockEntityAtLocation.tryAddElement(stack, state, world, pos)) {
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, BlockRegistry.POT_BLOCK_ENTITY_T, PotBlockEntity::tick);
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);

        PotBlockEntity blockEntity = (PotBlockEntity) world.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.updateState(newState);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CONTENTS);
    }



}
