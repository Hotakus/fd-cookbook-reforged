package net.hotakus.fdcookbook.utils;

import net.hotakus.fdcookbook.Constants;
import net.minecraft.resources.ResourceLocation;

public class utils {
    public static ResourceLocation make(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static ResourceLocation make(String modId, String name) {
        return new ResourceLocation(modId, name);
    }
}
