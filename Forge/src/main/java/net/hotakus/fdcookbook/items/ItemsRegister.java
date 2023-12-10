package net.hotakus.fdcookbook.items;

import net.hotakus.fdcookbook.Constants;
import net.hotakus.fdcookbook.FDCookBook;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FDCookBook.MOD_ID);

    public static final RegistryObject<Item> FD_COOKBOOK =
            ITEMS.register("fd_cookbook", CookBookItem::new);


    public static ResourceLocation make(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
