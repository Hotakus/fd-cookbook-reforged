package net.hotakus.fdcookbook.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.hotakus.fdcookbook.Constants;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegister {
    public static BlockEntityType<CookBookBlockEntity> COOKBOOK_BLOCK_ENTITY_TYPE;

    public static void registerBlockEntities() {
        COOKBOOK_BLOCK_ENTITY_TYPE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                utils.make("cookbook_block_entity"),
                FabricBlockEntityTypeBuilder
                        .create(CookBookBlockEntity::new, BlockRegister.FD_COOKBOOK_BLOCK)
                        .build(null)
                );
    }

    public static void register() {
        Constants.LOG.info("FD Cookbook [Info]: Registering Block Entities....");
        registerBlockEntities();
    }

}
