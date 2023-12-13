package net.hotakus.fdcookbook.api;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CBBlock extends Block implements EntityBlock {
    public CBBlock(Properties pProperties) {
        super(pProperties);
    }

    protected static InteractionResult leftClickToPickup(BlockState pState, Level pLevel, BlockPos pPos,
                                                         Player pPlayer, Item pItem, SoundEvent pSound) {
        Item item = pPlayer.getMainHandItem().getItem();

        if (pPlayer instanceof ServerPlayer player) {
            if (item != Items.AIR) {
                return InteractionResult.PASS;
            } else {
                pLevel.destroyBlock(pPos, false, player);
                pLevel.playSound(player, pPos, pSound, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                player.addItem(pItem.getDefaultInstance());
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}
