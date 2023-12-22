package net.hotakus.fdcookbook;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.hotakus.fdcookbook.blocks.BlockEntityRegister;
import net.hotakus.fdcookbook.blocks.BlockRegister;
import net.hotakus.fdcookbook.items.ItemsRegister;
import net.hotakus.fdcookbook.networking.ModMessages;

public class FDCookBookFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        ItemsRegister.register();
        BlockRegister.register();
        BlockEntityRegister.register();
        ModMessages.registerC2SPackets();

        // Some code like events require special initialization from the
        // loader specific code.
        ItemTooltipCallback.EVENT.register(CommonClass::onItemTooltip);
    }
}
