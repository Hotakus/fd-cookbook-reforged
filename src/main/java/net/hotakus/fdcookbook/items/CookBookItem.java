package net.hotakus.fdcookbook.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CookBookItem extends Item {
    public CookBookItem() {
        super(new Properties()
//                .tab(CreativeModeTab.TAB_MISC)
//                .stacksTo(1)
//                .rarity(Rarity.EPIC)
        );
    }

//    @Override
//    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
//        pTooltipComponents.add(new TextComponent("test"));
//    }
}
