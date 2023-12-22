package net.hotakus.fdcookbook.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static net.hotakus.fdcookbook.items.ItemsMappingToEntry.openEntry;

public class OpenEntryC2SPacket extends Packets {

    public class EntryData {
        private UUID uuid = null;
        private ResourceLocation entryRL = null;
        private int entryPage = 0;
    }

    private static Map<UUID, Map.Entry<ResourceLocation, Integer>> playersEntries = new HashMap<>();
    EntryData entryData = new EntryData();
    EntryData entryDataReceived;

//    public Map<UUID, Map.Entry<ResourceLocation, Integer>> getPlayersEntries() {
//        return playersEntries;
//    }

    public void setBookEntry(UUID _uuid, ResourceLocation _entryRL, int _entryPage) {
        entryData.entryRL = _entryRL;
        entryData.entryPage = _entryPage;
        entryData.uuid = _uuid;
    }

//    static boolean test = false;
//
//    public void addPlayerEntry(UUID _player, Map.Entry<ResourceLocation, Integer> _entry) {
//
//        if (!playersEntries.containsKey(_player)) {
//            System.out.println("Adding " + _entry.getKey().getPath() + " to " + _player);
//            playersEntries.put(_player, _entry);
//            test = true;
//        } else {
//            System.out.println("Replacing " + _entry.getKey().getPath() + " to " + _player);
//            playersEntries.replace(_player, _entry);
//            test = true;
//        }
//    }

    public OpenEntryC2SPacket() {
        super();
    }

    public OpenEntryC2SPacket(FriendlyByteBuf buf) {
        entryDataReceived = new EntryData();
        entryDataReceived.uuid = buf.readUUID();
        entryDataReceived.entryRL = buf.readResourceLocation();
        entryDataReceived.entryPage = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(entryData.uuid);
        buf.writeResourceLocation(entryData.entryRL);
        buf.writeInt(entryData.entryPage);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (entryDataReceived.uuid.equals(player.getUUID())) {
                openEntry(player, entryDataReceived.entryRL, entryDataReceived.entryPage);
            }
        });
        return true;
    }
}
