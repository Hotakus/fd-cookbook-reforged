package net.hotakus.fdcookbook;

import com.mojang.logging.LogUtils;
import net.hotakus.fdcookbook.blocks.BlockEntityRegister;
import net.hotakus.fdcookbook.blocks.BlockRegister;
import net.hotakus.fdcookbook.items.ItemsRegister;
import net.hotakus.fdcookbook.networking.ModMessages;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FDCookBook.MOD_ID)
public class FDCookBook {

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "fdcookbook";

    public FDCookBook() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemsRegister.register(eventBus);
        BlockRegister.register(eventBus);
        BlockEntityRegister.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Some preinit code
        LOGGER.info("HELLO FROM PREINIT");

        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
//        ItemBlockRenderTypes.setRenderLayer(BlockRegister.FD_COOKBOOK_BLOCK.get(), RenderType.cutoutMipped());
//        ItemBlockRenderTypes.setRenderLayer(BlockRegister.FD_COOKBOOK_BAKED_BLOCK.get(), RenderType.cutoutMipped());
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//        // Do something when the server starts
//        // LOGGER.info("HELLO from server starting");
//    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
//    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class RegistryEvents {
//        @SubscribeEvent
//        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
//            // Register a new block here
////            LOGGER.info("HELLO from Register Block");
//        }
//    }
}
