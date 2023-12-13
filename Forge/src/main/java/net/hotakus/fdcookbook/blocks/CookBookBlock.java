package net.hotakus.fdcookbook.blocks;

import net.hotakus.fdcookbook.api.CBBlock;
import net.hotakus.fdcookbook.items.ItemsRegister;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.hotakus.fdcookbook.blocks.CookBookBlockEntity.updateTicks;


public class CookBookBlock extends CBBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_NORTH_AND_SOUTH = Block.box(3.775D, 0.0D, 2.5D, 12.225D, 2.25D, 13.5D);
    private static final VoxelShape SHAPE_EAST_AND_WEST = Block.box(2.5D, 0.0D, 3.775D, 13.5D, 2.25D, 12.225D);

    // private static final VoxelShape SHAPE_LEAN_AGAINST;

    private static final BooleanProperty IS_LEAN_AGAINST = BooleanProperty.create("lean_against");

    public CookBookBlock() {
        super(BlockBehaviour.Properties
                .of(Material.WOOL)
                .noOcclusion()
                .strength(3)
                .sound(SoundType.WOOL)
                .requiresCorrectToolForDrops()
        );

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(IS_LEAN_AGAINST, false)
        );
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        InteractionResult res = InteractionResult.PASS;

        if (pHand == InteractionHand.MAIN_HAND) {
            if (pPlayer instanceof ServerPlayer player) {
                PatchouliAPI.get().openBookGUI(player, utils.make("fd_cookbook"));
                res = InteractionResult.SUCCESS;
            }
        }

        return res;
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        leftClickToPickup(pState, pLevel, pPos, pPlayer, ItemsRegister.FD_COOKBOOK.get(), SoundEvents.WOOD_BREAK);
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
        pBuilder.add(IS_LEAN_AGAINST);
    }

    static int cook_time_needed = 5 * (20); // Cook needs 5s
    //    static ArrayList<Map.Entry<BlockPos, Integer>> cookPos = new ArrayList<>();
    static Map<BlockPos, Integer> cookMap = new HashMap<>();
    static boolean cookMapIsUsing = false;

    public static void clearCookMap() {
        if (!cookMapIsUsing) {
            var iter = cookMap.entrySet().iterator();
            while (iter.hasNext()) {
                var entry = iter.next();
                if (entry.getValue() == 0) {
                    cookMapIsUsing = true;
                    iter.remove();
                    cookMapIsUsing = false;
                }
            }
        }
    }

    public static void cookCheck(Level pLevel, BlockPos pPos) {

        if (pLevel.isClientSide) {
            return;
        }

//        System.out.println("current size: " + cookMap.size());
        cookMapIsUsing = true;
        cookMap.putIfAbsent(pPos, 0);
        for (Map.Entry<BlockPos, Integer> entry : cookMap.entrySet()) {
            if (pPos.equals(entry.getKey())) {
                entry.setValue(entry.getValue() + updateTicks);
                if (entry.getValue() >= cook_time_needed) {
//                    System.out.println("cook");
                    BlockState selfState = pLevel.getBlockState(entry.getKey());
                    pLevel.destroyBlock(entry.getKey(), false);
                    pLevel.setBlockAndUpdate(entry.getKey(),
                            BlockRegister.FD_COOKBOOK_BAKED_BLOCK.get().defaultBlockState().setValue(FACING, selfState.getValue(FACING))
                    );
                    entry.setValue(0);
                    //cookMap.remove(entry.getKey());
                } else {
//                    System.out.println("Block pos: " + entry.getKey() + "are cooking... " + "(" + cookMap.size() + ")" +
//                            "time left: " + entry.getValue());
                }
            }
        }
        cookMapIsUsing = false;
    }

    public static void checkBelowBlock(Level pLevel, BlockPos pPos) {

        BlockState state = pLevel.getBlockState(pPos.below());

        if (pLevel.isClientSide) {
            return;
        }

        if (state.getBlock() == Blocks.AIR || state.getBlock() == Blocks.WATER) {
            //System.out.println("stove");
            pLevel.destroyBlock(pPos, false);
            pLevel.addFreshEntity(new ItemEntity(
                    pLevel, pPos.getX(), pPos.getY(), pPos.getZ(),
                    ItemsRegister.FD_COOKBOOK.get().getDefaultInstance())
            );
            pLevel.playSound(null, pPos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            if (Objects.equals(state.getBlock().getRegistryName(), utils.make("farmersdelight", "stove"))) {
                //System.out.println(pPos + "on stove");
                cookCheck(pLevel, pPos);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CookBookBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == BlockEntityRegister.COOKBOOK_BLOCK_ENTITY.get() ?
                CookBookBlockEntity::tick : null;
    }

}
