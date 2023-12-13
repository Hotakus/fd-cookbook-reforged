package net.hotakus.fdcookbook.items;

import net.hotakus.fdcookbook.api.CBItem;
import net.hotakus.fdcookbook.blocks.BlockRegister;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
        super(new Properties()
                .tab(CreativeModeTab.TAB_FOOD)
                .stacksTo(1)
                .food(new FoodProperties.Builder()
                        .alwaysEat()
                        .nutrition(6)
                        .saturationMod(0.0f)
                        .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 3600, 4), 1f)
                        .build()
                )
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (Screen.hasShiftDown()) {
            placeBlock(pContext, BlockRegister.FD_COOKBOOK_BAKED_BLOCK.get());
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("tooltip.fdcookbook.fdcookbook_baked.tooltip.normal"));
    }
}
