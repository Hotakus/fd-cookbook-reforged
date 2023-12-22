package net.hotakus.fdcookbook.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hotakus.fdcookbook.networking.packets.OpenEntryC2SPacket;
import net.hotakus.fdcookbook.networking.packets.OpenGuiC2SPacket;
import net.hotakus.fdcookbook.networking.packets.PlaceCBBlockC2SPacket;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ModMessages {

    public static final ResourceLocation COOKBOOK_OPEN_GUI_ID = utils.make("cookbook_open_gui");
    public static final ResourceLocation COOKBOOK_OPEN_ENTRY_ID = utils.make("cookbook_open_entry");
    public static final ResourceLocation COOKBOOK_PLACE_BLOCK_ID = utils.make("cookbook_place_block");


    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(COOKBOOK_OPEN_GUI_ID, OpenGuiC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(COOKBOOK_OPEN_ENTRY_ID, OpenEntryC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(COOKBOOK_PLACE_BLOCK_ID, PlaceCBBlockC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }

    public static void sendToServer(ResourceLocation id, FriendlyByteBuf buf) {
        ClientPlayNetworking.send(id, buf);
    }
}
