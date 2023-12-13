package net.hotakus.fdcookbook.api;

import net.hotakus.fdcookbook.blocks.CookBookBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class CBItem extends Item {
    public CBItem(Properties properties) {
        super(properties);
    }

    protected static InteractionResult placeBlock(UseOnContext ctx, Block block) {

        BlockPos pos = ctx.getClickedPos();
        Level level = ctx.getLevel();
        Direction dir = ctx.getClickedFace();
        InteractionResult res = InteractionResult.PASS;

        // check neighbors' block
        Map<Direction, BlockState> dirMap = new HashMap<>();
        dirMap.put(Direction.NORTH, level.getBlockState(pos.north()));
        dirMap.put(Direction.SOUTH, level.getBlockState(pos.south()));
        dirMap.put(Direction.EAST, level.getBlockState(pos.east()));
        dirMap.put(Direction.WEST, level.getBlockState(pos.west()));
        dirMap.put(Direction.UP, level.getBlockState(pos.above()));
        dirMap.put(Direction.DOWN, level.getBlockState(pos.below()));

        Map<Direction, BlockPos> posMap = new HashMap<>();
        posMap.put(Direction.NORTH, pos.north());
        posMap.put(Direction.SOUTH, pos.south());
        posMap.put(Direction.EAST, pos.east());
        posMap.put(Direction.WEST, pos.west());
        posMap.put(Direction.UP, pos.above());
        posMap.put(Direction.DOWN, pos.below());

        BlockState operationState = dirMap.get(dir);

        if (ctx.getPlayer() instanceof ServerPlayer player) {
            if (operationState.getBlock() == Blocks.AIR) {
                level.setBlockAndUpdate(posMap.get(dir), block.defaultBlockState()
                        .setValue(CookBookBlock.FACING, player.getDirection().getOpposite()));
                level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.getItemInHand(ctx.getHand()).shrink(1);
                res = InteractionResult.SUCCESS;
            }
        }

        return res;
    }
}
