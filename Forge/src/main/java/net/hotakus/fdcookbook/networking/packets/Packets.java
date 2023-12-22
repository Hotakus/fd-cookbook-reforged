package net.hotakus.fdcookbook.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Packets {
    public Packets() {

    }

    public Packets(FriendlyByteBuf buf) {

    }


    public void encode(FriendlyByteBuf buf) {

    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        return false;
    }
}
