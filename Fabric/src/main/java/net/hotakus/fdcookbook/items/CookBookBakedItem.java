package net.hotakus.fdcookbook.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.hotakus.fdcookbook.api.CBItem;
import net.hotakus.fdcookbook.blocks.BlockRegister;
import net.hotakus.fdcookbook.networking.ModMessages;
import net.hotakus.fdcookbook.networking.packets.PlaceCBBlockC2SPacket;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CookBookBakedItem extends CBItem {
    public CookBookBakedItem() {
        super(new FabricItemSettings()
                .tab(CreativeModeTab.TAB_FOOD)
                .stacksTo(1)
                .food(new FoodProperties.Builder()
                        .alwaysEat()
                        .nutrition(6)
                        .saturationMod(0.0f)
                        .effect(new MobEffectInstance(MobEffects.HUNGER, 3600, 4), 1f)
                        .build()
                )
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) {
            if (Screen.hasShiftDown()) {
                var packet = new PlaceCBBlockC2SPacket();
                var placeEntry = new PlaceCBBlockC2SPacket.PlaceEntry(
                        pContext.getClickedPos(), BlockRegister.FD_COOKBOOK_BAKED_BLOCK.getLootTable(), pContext.getClickedFace());
                packet.addPlayerPlace(pContext.getPlayer().getUUID(), placeEntry);
                ModMessages.sendToServer(ModMessages.COOKBOOK_PLACE_BLOCK_ID, packet.getBuf());
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.fdcookbook.fdcookbook_baked.tooltip.normal"));
    }
}
