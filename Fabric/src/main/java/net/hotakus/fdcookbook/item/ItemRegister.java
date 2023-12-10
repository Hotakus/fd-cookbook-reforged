package net.hotakus.fdcookbook.item;

import net.hotakus.fdcookbook.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegister {

    public static Item CookBook = registerItem("fd_cookbook", new CookBookItem());


    public ItemRegister() {}

    public static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, make(name), item);
    }

    public static ResourceLocation make(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static void register() {
        Constants.LOG.info("Registering" + Constants.MOD_ID + " Items");
    }
}
