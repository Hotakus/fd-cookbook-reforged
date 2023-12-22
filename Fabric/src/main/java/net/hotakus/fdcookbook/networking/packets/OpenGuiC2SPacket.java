package net.hotakus.fdcookbook.networking.packets;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import vazkii.patchouli.api.PatchouliAPI;

public class OpenGuiC2SPacket {
    private FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());

    public FriendlyByteBuf getBuf() {
        return buf;
    }

    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf friendlyByteBuf, PacketSender packetSender) {
        PatchouliAPI.get().openBookGUI(player, utils.make("fd_cookbook"));
    }
}
