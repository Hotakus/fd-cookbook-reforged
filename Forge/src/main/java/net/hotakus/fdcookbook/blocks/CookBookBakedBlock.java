package net.hotakus.fdcookbook.blocks;

import net.hotakus.fdcookbook.api.CBBlock;
import net.hotakus.fdcookbook.items.ItemsRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CookBookBakedBlock extends CBBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_NORTH_AND_SOUTH = Block.box(3.775D, 0.0D, 2.5D, 12.225D, 2.25D, 13.5D);
    private static final VoxelShape SHAPE_EAST_AND_WEST = Block.box(2.5D, 0.0D, 3.775D, 13.5D, 2.25D, 12.225D);

    public CookBookBakedBlock() {
        super(BlockBehaviour.Properties
                .copy(Blocks.WHITE_WOOL)
                .noOcclusion()
                .strength(3)
                .sound(SoundType.WOOL)
                .requiresCorrectToolForDrops()
        );

        this.registerDefaultState(this.stateDefinition.any()
                //.setValue(IS_LEAN_AGAINST, false)
        );

    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        leftClickToPickup(pState, pLevel, pPos, pPlayer, ItemsRegister.FD_COOKBOOK_BAKED.get(), SoundEvents.WOOD_BREAK);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction dir = pState.getValue(FACING);
        return (dir == Direction.SOUTH || dir == Direction.NORTH) ? SHAPE_NORTH_AND_SOUTH : SHAPE_EAST_AND_WEST;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }


    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
