package net.hotakus.fdcookbook.networking.packets;

import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.hotakus.fdcookbook.api.CBItem;
import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaceCBBlockC2SPacket {
    public static class PlaceEntry {
        public BlockPos pos;
        public ResourceLocation blockRL;
        public Direction dir;

        public PlaceEntry() {
        }

        public PlaceEntry(BlockPos _pos, ResourceLocation _block, Direction _dir) {
            pos = _pos;
            blockRL = _block;
            dir = _dir;
        }
    }

    private FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
    private Map<UUID, PlaceEntry> playersPlace = new HashMap<>();

    public FriendlyByteBuf getBuf() {
        return buf;
    }

    public void encode() {
        buf.writeMap(playersPlace, FriendlyByteBuf::writeUUID, (_buf, _entry) -> {
            _buf.writeBlockPos(_entry.pos);
            _buf.writeResourceLocation(_entry.blockRL);
            _buf.writeEnum(_entry.dir);
        });
    }

    public void addPlayerPlace(UUID _player, PlaceEntry _entry) {
        if (!playersPlace.containsKey(_player)) {
            playersPlace.put(_player, _entry);
        } else {
            playersPlace.replace(_player, _entry);
        }
        encode();
    }

    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf friendlyByteBuf, PacketSender packetSender) {
        var playersPlace = friendlyByteBuf.readMap(Maps::newHashMapWithExpectedSize, FriendlyByteBuf::readUUID, (_buf) -> {
            var res = new PlaceEntry();
            res.pos = _buf.readBlockPos();
            res.blockRL = _buf.readResourceLocation();
            res.dir = _buf.readEnum(Direction.class);
            return res;
        });

        boolean hasEntry = playersPlace.containsKey(player.getUUID());
        if (!hasEntry) {
            player.sendMessage(new TextComponent("No entry to place"), player.getUUID());
            return;
        }

        var entry = playersPlace.get(player.getUUID());
        String otherCheckingString = "blocks/";
        String RLPath = entry.blockRL.getPath();
        ResourceLocation newRL = entry.blockRL;
        if (RLPath.contains(otherCheckingString)) {
            String willPlaceRL = RLPath.substring(RLPath.indexOf(otherCheckingString) + otherCheckingString.length());
            newRL = utils.make(willPlaceRL);
        }

        CBItem.placeBlock(entry.pos, Registry.BLOCK.get(newRL), entry.dir, player);
    }

}
