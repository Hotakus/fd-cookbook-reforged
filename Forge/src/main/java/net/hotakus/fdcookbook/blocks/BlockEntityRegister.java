package net.hotakus.fdcookbook.blocks;

import net.hotakus.fdcookbook.Constants;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Constants.MOD_ID);

    public static RegistryObject<BlockEntityType<CookBookBlockEntity>> COOKBOOK_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("cookbook_block_entity",
                    () -> BlockEntityType.Builder
                            .of(
                                    CookBookBlockEntity::new,
                                    BlockRegister.FD_COOKBOOK_BLOCK.get()
                            ).build(null)
            );



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
