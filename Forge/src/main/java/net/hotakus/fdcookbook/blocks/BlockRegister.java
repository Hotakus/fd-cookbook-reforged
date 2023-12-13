package net.hotakus.fdcookbook.blocks;

import net.hotakus.fdcookbook.FDCookBook;
import net.hotakus.fdcookbook.items.ItemsRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegister {
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FDCookBook.MOD_ID);

    public static final RegistryObject<Block> FD_COOKBOOK_BLOCK = BLOCKS.register("fdcookbook", CookBookBlock::new);
    public static final RegistryObject<Block> FD_COOKBOOK_BAKED_BLOCK = BLOCKS.register("fdcookbook_baked",
            CookBookBakedBlock::new);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ItemsRegister.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties().tab(tab))
        );
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
