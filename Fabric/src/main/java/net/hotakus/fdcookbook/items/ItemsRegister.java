package net.hotakus.fdcookbook.items;

import net.hotakus.fdcookbook.Constants;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class ItemsRegister {
    public static Item FD_COOKBOOK = registerItem("fd_cookbook", new CookBookItem());
    public static Item FD_COOKBOOK_BAKED = registerItem("fdcookbook_baked", new CookBookBakedItem());


    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, utils.make(name), item);
    }

    public static void register() {
        Constants.LOG.info("FD Cookbook [Info]: Registering Items....");
    }
}
