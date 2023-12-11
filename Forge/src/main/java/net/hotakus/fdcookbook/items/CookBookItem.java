package net.hotakus.fdcookbook.items;

import net.hotakus.fdcookbook.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.Map;

import static net.hotakus.fdcookbook.items.ItemsMappingToEntry.*;

public class CookBookItem extends Item {

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

        if (pUsedHand == InteractionHand.MAIN_HAND && !Screen.hasShiftDown()) {
            if (pPlayer instanceof ServerPlayer) {
                PatchouliAPI.get().openBookGUI((ServerPlayer) pPlayer, Registry.ITEM.getKey(this));
                res = InteractionResult.SUCCESS;
            }
        }

        return new InteractionResultHolder<>(res, stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        InteractionResult res = InteractionResult.PASS;

        if (Screen.hasShiftDown()) {
            BlockPos pos = pContext.getClickedPos();
            Level level = pContext.getLevel();
            BlockState blockState = level.getBlockState(pos);
            ResourceLocation registryName = blockState.getBlock().getRegistryName();
            Player player = pContext.getPlayer();

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
        }

        return res;
    }

    public static Component getEdition() {
        try {
            return PatchouliAPI.get().getSubtitle(new ResourceLocation(Constants.MOD_ID + ":" + "fd_cookbook"));
            //return new TextComponent("");
        } catch (IllegalArgumentException e) {
            return new TextComponent(""); // TODO Adjust Patchouli because first search tree creation is too early to get the edition
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
        }
    }
}
