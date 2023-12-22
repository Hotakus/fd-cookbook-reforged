package net.hotakus.fdcookbook;

import net.hotakus.fdcookbook.blocks.BlockEntityRegister;
import net.hotakus.fdcookbook.blocks.CookBookBER;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
//    @SubscribeEvent
    public static void registerRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegister.COOKBOOK_BLOCK_ENTITY.get(), CookBookBER::new);
    }
}
