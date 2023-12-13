package net.hotakus.fdcookbook.items;

import net.hotakus.fdcookbook.api.CBItem;
import net.hotakus.fdcookbook.blocks.BlockRegister;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.Map;

import static net.hotakus.fdcookbook.items.ItemsMappingToEntry.*;

public class CookBookItem extends CBItem {

    public CookBookItem() {
        super(new Properties()
                .tab(CreativeModeTab.TAB_FOOD)
                .stacksTo(1)
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        InteractionResult res = InteractionResult.PASS;

        if (pUsedHand == InteractionHand.MAIN_HAND && !Screen.hasShiftDown() && !Screen.hasAltDown()) {
            if (pPlayer instanceof ServerPlayer) {
                PatchouliAPI.get().openBookGUI((ServerPlayer) pPlayer, utils.make("fd_cookbook"));
                res = InteractionResult.SUCCESS;
            }
        }

        return new InteractionResultHolder<>(res, stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        InteractionResult res = InteractionResult.PASS;

        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState blockState = level.getBlockState(pos);
        Player player = pContext.getPlayer();

        if (player instanceof ServerPlayer) {
            if (Screen.hasShiftDown() && !Screen.hasAltDown()) {
                ResourceLocation registryName = blockState.getBlock().getRegistryName();
                pContext.getPlayer().swing(pContext.getHand(), true);

                if (isCookBookItem(registryName)) {
                    Map.Entry<ResourceLocation, Integer> entry = getEntryLocation(registryName);
                    if (player instanceof ServerPlayer) {
                        if (entry != null) {
                            openEntry((ServerPlayer) player, entry.getKey(), entry.getValue());
//                        player.sendMessage(new TextComponent("Opening " + entry.getKey() + " page " + entry.getValue()),
//                                pContext.getPlayer().getUUID());
                            res = InteractionResult.SUCCESS;
                        } else {
//                        player.sendMessage(new TextComponent("No mapping for " + registryName),
//                                pContext.getPlayer().getUUID());
                        }
                    }
                }
            } else if ((Screen.hasShiftDown() && Screen.hasAltDown()) || Screen.hasAltDown()) {
                res = placeBlock(pContext, BlockRegister.FD_COOKBOOK_BLOCK.get());
            }
        }

        return res;
    }

    public static Component getEdition() {
        try {
            return PatchouliAPI.get().getSubtitle(utils.make("fd_cookbook"));
        } catch (IllegalArgumentException e) {
            return new TextComponent("");
        }
    }

    public static Component getTitle(ItemStack stack) {
        Component title = stack.getHoverName();

        String akashicTomeNBT = "akashictome:displayName";
        if (stack.hasTag() && stack.getTag().contains(akashicTomeNBT)) {
            title = Component.Serializer.fromJson(stack.getTag().getString(akashicTomeNBT));
        }

        return title;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(getEdition().copy().withStyle(ChatFormatting.GRAY));
        if (!Screen.hasShiftDown()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.shift"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.fd_cookbook.tooltip.normal"));
            pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.fd_cookbook.tooltip.normal2"));
            pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.fd_cookbook.tooltip.normal3"));
            pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.fd_cookbook.tooltip.normal4"));
        }
    }
}
