package net.hotakus.fdcookbook.networking.packets;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.UUID;

import static net.hotakus.fdcookbook.items.ItemsMappingToEntry.openEntry;

public class OpenEntryC2SPacket {
    public class EntryData {
        private UUID uuid = null;
        private ResourceLocation entryRL = null;
        private int entryPage = 0;
    }

    private EntryData entryData = new EntryData();
    private FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());

    public FriendlyByteBuf getBuf() {
        return buf;
    }

    public void setBookEntry(UUID _uuid, ResourceLocation _entryRL, int _entryPage) {
        entryData.entryRL = _entryRL;
        entryData.entryPage = _entryPage;
        entryData.uuid = _uuid;
        encode();
    }

    public void encode() {
        buf.writeUUID(entryData.uuid);
        buf.writeResourceLocation(entryData.entryRL);
        buf.writeInt(entryData.entryPage);
    }

    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf friendlyByteBuf, PacketSender packetSender) {
        if (friendlyByteBuf.readUUID().equals(player.getUUID())) {
            ResourceLocation entryRL = friendlyByteBuf.readResourceLocation();
            int entryPage = friendlyByteBuf.readInt();

            openEntry(player, entryRL, entryPage);
        }
    }
}
