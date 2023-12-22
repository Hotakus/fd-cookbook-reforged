package net.hotakus.fdcookbook.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.hotakus.fdcookbook.api.CBItem;
import net.hotakus.fdcookbook.blocks.BlockRegister;
import net.hotakus.fdcookbook.networking.ModMessages;
import net.hotakus.fdcookbook.networking.packets.OpenEntryC2SPacket;
import net.hotakus.fdcookbook.networking.packets.OpenGuiC2SPacket;
import net.hotakus.fdcookbook.networking.packets.PlaceCBBlockC2SPacket;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
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

import static net.hotakus.fdcookbook.items.ItemsMappingToEntry.getEntryLocation;
import static net.hotakus.fdcookbook.items.ItemsMappingToEntry.isCookBookItem;

public class CookBookItem extends CBItem {

    public CookBookItem() {
        super(new FabricItemSettings()
                .tab(CreativeModeTab.TAB_FOOD)
                .stacksTo(1)
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        InteractionResult res = InteractionResult.PASS;

        if (pLevel.isClientSide) {
            if (pUsedHand == InteractionHand.MAIN_HAND && !Screen.hasShiftDown() && !Screen.hasAltDown()) {
                //pPlayer.sendMessage(new TextComponent("Open GUI"), pPlayer.getUUID());
                ModMessages.sendToServer(ModMessages.COOKBOOK_OPEN_GUI_ID, new OpenGuiC2SPacket().getBuf());
                res = InteractionResult.SUCCESS;
            }
        } else {
            res = InteractionResult.PASS;
        }

        return new InteractionResultHolder<>(res, stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        InteractionResult res = InteractionResult.PASS;

        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState blockState = level.getBlockState(pos);

        if (level.isClientSide) {
            if (Screen.hasShiftDown() && !Screen.hasAltDown()) {
//                pContext.getPlayer().sendMessage(new TextComponent("hasShiftDown"),
//                        pContext.getPlayer().getUUID());
                ResourceLocation registryName = blockState.getBlock().getLootTable();
                pContext.getPlayer().swing(pContext.getHand(), true);
                if (isCookBookItem(registryName)) {
                    Map.Entry<ResourceLocation, Integer> entry = getEntryLocation(registryName);
                    if (entry != null) {
                        var packet = new OpenEntryC2SPacket();
                        packet.setBookEntry(pContext.getPlayer().getUUID(), entry.getKey(), entry.getValue());
                        ModMessages.sendToServer(ModMessages.COOKBOOK_OPEN_ENTRY_ID, packet.getBuf());
                    } else {
                        pContext.getPlayer().sendMessage(new TextComponent("Entry not found: " + registryName),
                                pContext.getPlayer().getUUID());
                        res = InteractionResult.PASS;
                    }
                    res = InteractionResult.SUCCESS;
                }
            } else if (Screen.hasAltDown()) {
//                pContext.getPlayer().sendMessage(new TextComponent("hasAltDown!"), pContext.getPlayer().getUUID());
                var packet = new PlaceCBBlockC2SPacket();
                var placeEntry = new PlaceCBBlockC2SPacket.PlaceEntry(
                        pos, BlockRegister.FD_COOKBOOK_BLOCK.getLootTable(), pContext.getClickedFace());
                packet.addPlayerPlace(pContext.getPlayer().getUUID(), placeEntry);
                ModMessages.sendToServer(ModMessages.COOKBOOK_PLACE_BLOCK_ID, packet.getBuf());
                res = InteractionResult.SUCCESS;
            }
        } else {

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
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag
            pIsAdvanced) {
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
