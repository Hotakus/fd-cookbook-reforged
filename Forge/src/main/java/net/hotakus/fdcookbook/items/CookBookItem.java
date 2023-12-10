package net.hotakus.fdcookbook.items;

import net.hotakus.fdcookbook.FDCookBook;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

public class CookBookItem extends Item {

    public CookBookItem() {
        super(new Properties()
                .tab(CreativeModeTab.TAB_FOOD)
                .stacksTo(1)
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        InteractionResult res = InteractionResult.PASS;

        if (pUsedHand == InteractionHand.MAIN_HAND && !Screen.hasShiftDown()) {
            if (pPlayer instanceof ServerPlayer) {
                PatchouliAPI.get().openBookGUI((ServerPlayer) pPlayer, Registry.ITEM.getKey(this));
                res = InteractionResult.SUCCESS;
            }
        } else if (Screen.hasShiftDown()) {
            // System.out.println();
        } else {

        }

        return new InteractionResultHolder<>(res, stack);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {

        InteractionResult res = InteractionResult.PASS;

        if (Screen.hasShiftDown()) {
            BlockPos pos = pContext.getClickedPos();
            Level level = pContext.getLevel();

            BlockState blockState = level.getBlockState(pos);

//            System.out.println(blockState.getBlock().getRegistryName().getPath());
//            pContext.getPlayer().sendMessage(new TextComponent(blockState.getBlock().getRegistryName().getPath()),
//                    pContext.getPlayer().getUUID());

            pContext.getPlayer().swing(pContext.getHand(), true);

            res = InteractionResult.SUCCESS;
        }

        // TODO: Mapping items to Entries
        // PatchouliAPI.get().openBookEntry(bookRL,);

        return res;
    }

    public static @NotNull Component getEdition() {
        try {
            return PatchouliAPI.get().getSubtitle(new ResourceLocation(FDCookBook.MOD_ID + ":" + "fd_cookbook"));
        } catch (IllegalArgumentException e) {
            return new TextComponent(""); // TODO Adjust Patchouli because first search tree creation is too early to get the edition
        }
    }

    public static Component getTitle(@NotNull ItemStack stack) {
        Component title = stack.getHoverName();

        String akashicTomeNBT = "akashictome:displayName";
        if (stack.hasTag() && stack.getTag().contains(akashicTomeNBT)) {
            title = Component.Serializer.fromJson(stack.getTag().getString(akashicTomeNBT));
        }

        return title;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(getEdition().copy().withStyle(ChatFormatting.GRAY));
//        if (!Screen.hasShiftDown()) {
//            pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.shift"));
//        } else {
//            pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.fd_cookbook.tooltip.normal"));
//        }
    }
}
