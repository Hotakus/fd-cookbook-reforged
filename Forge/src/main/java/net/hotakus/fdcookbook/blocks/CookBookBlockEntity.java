package net.hotakus.fdcookbook.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static net.hotakus.fdcookbook.blocks.CookBookBlock.checkBelowBlock;
import static net.hotakus.fdcookbook.blocks.CookBookBlock.clearCookMap;

public class CookBookBlockEntity extends BlockEntity {

    public CookBookBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.COOKBOOK_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    static int ticks = 0;
    static int updateTicks = 5; // Custom Config

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        CookBookBlockEntity tile = (CookBookBlockEntity) be;

        ticks++;
        if (ticks >= updateTicks) {
            ticks = 0;
            checkBelowBlock(level, pos);
            clearCookMap();
        }
    }
}
