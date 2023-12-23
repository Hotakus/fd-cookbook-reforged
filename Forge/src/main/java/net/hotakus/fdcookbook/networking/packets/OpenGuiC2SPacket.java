package net.hotakus.fdcookbook.networking.packets;

import net.hotakus.fdcookbook.utils.utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.Supplier;

public class OpenGuiC2SPacket extends Packets {
    public OpenGuiC2SPacket() {
        super();
    }

    public OpenGuiC2SPacket(FriendlyByteBuf buf) {
        super();
    }

    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            // ServerLevel level = player.getLevel();
            if (player != null) {
                PatchouliAPI.get().openBookGUI(player, utils.make("fd_cookbook"));
            }
        });
        return true;
    }

}
