package net.hotakus.fdcookbook.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.hotakus.fdcookbook.Constants;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockRegister {
    public static final Block FD_COOKBOOK_BLOCK = registerBlock("fdcookbook", new CookBookBlock(), null, false);
    public static final Block FD_COOKBOOK_BAKED_BLOCK = registerBlock("fdcookbook_baked", new CookBookBakedBlock(),
            null, false);

    private static Block registerBlock(String name, Block block, CreativeModeTab tab, boolean registerItem) {
        if (registerItem) {
            registerBlockItem(name, block, tab);
        }
        return Registry.register(Registry.BLOCK, utils.make(name), block);
    }

    private static Item registerBlockItem(String name, Block block, CreativeModeTab tab) {
        return Registry.register(Registry.ITEM, utils.make(name), new BlockItem(block, new FabricItemSettings().tab(tab)));
    }


    public static void register() {
        Constants.LOG.info("FD Cookbook [Info]: Registering Blocks....");
    }
}
