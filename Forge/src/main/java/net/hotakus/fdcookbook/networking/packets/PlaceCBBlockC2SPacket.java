package net.hotakus.fdcookbook.networking.packets;

import com.google.common.collect.Maps;
import net.hotakus.fdcookbook.api.CBItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class PlaceCBBlockC2SPacket extends Packets {

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

    private static Map<UUID, PlaceEntry> playersPlace = new HashMap<>();


    public void addPlayerPlace(UUID _player, PlaceEntry _entry) {
        if (!playersPlace.containsKey(_player)) {
            playersPlace.put(_player, _entry);
        } else {
            playersPlace.replace(_player, _entry);
        }
    }

    public PlaceCBBlockC2SPacket() {

    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeMap(playersPlace, FriendlyByteBuf::writeUUID, (_buf, _entry) -> {
            _buf.writeBlockPos(_entry.pos);
            _buf.writeResourceLocation(_entry.blockRL);
            _buf.writeEnum(_entry.dir);
        });
    }


    public PlaceCBBlockC2SPacket(FriendlyByteBuf buf) {
        playersPlace = buf.readMap(Maps::newHashMapWithExpectedSize, FriendlyByteBuf::readUUID, (_buf) -> {
            var res = new PlaceEntry();
            res.pos = _buf.readBlockPos();
            res.blockRL = _buf.readResourceLocation();
            res.dir = _buf.readEnum(Direction.class);
            return res;
        });
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            var entry = playersPlace.get(player.getUUID());
            if (entry != null) {
                // player.sendMessage(new TextComponent("Placing " + entry.blockRL), player.getUUID());
                CBItem.placeBlock(entry.pos, ForgeRegistries.BLOCKS.getValue(entry.blockRL), entry.dir, player);
            }
        });
        return true;
    }

}
