package net.hotakus.fdcookbook.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

public class CookBookItem extends Item {
    public CookBookItem() {
        super(new Properties()
                .tab(CreativeModeTab.TAB_MISC)
                .stacksTo(1)
                .rarity(Rarity.EPIC)
        );
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (playerIn instanceof ServerPlayer player) {
            //UseItemSuccessTrigger.INSTANCE.trigger(player, stack, player.serverLevel(), player.getX(), player.getY()
                    //, player.getZ());
            //PatchouliAPI.get().openBookGUI(player, BuiltInRegistries.ITEM.getKey(this));
            //playerIn.playSound(BotaniaSounds.lexiconOpen, 1F, (float) (0.7 + Math.random() * 0.4));
        }

        return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.fd_cookbook"));
    }
}
