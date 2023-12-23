package net.hotakus.fdcookbook.networking;

import net.hotakus.fdcookbook.networking.packets.OpenEntryC2SPacket;
import net.hotakus.fdcookbook.networking.packets.OpenGuiC2SPacket;
import net.hotakus.fdcookbook.networking.packets.PlaceCBBlockC2SPacket;
import net.hotakus.fdcookbook.networking.packets.PlayerActionsC2SPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static net.hotakus.fdcookbook.utils.utils.make;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static final String version = "1.0";
    private static int paketID = 0;

    public static int id() {
        return paketID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(make("messages"))
                .networkProtocolVersion(() -> version)
                .clientAcceptedVersions(version::equals)
                .serverAcceptedVersions(version::equals)
                .simpleChannel();

        net.messageBuilder(OpenGuiC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenGuiC2SPacket::new)
                .encoder(OpenGuiC2SPacket::encode)
                .consumerMainThread(OpenGuiC2SPacket::handle).add();

        net.messageBuilder(OpenEntryC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenEntryC2SPacket::new)
                .encoder(OpenEntryC2SPacket::encode)
                .consumerMainThread(OpenEntryC2SPacket::handle).add();

        net.messageBuilder(PlaceCBBlockC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlaceCBBlockC2SPacket::new)
                .encoder(PlaceCBBlockC2SPacket::encode)
                .consumerMainThread(PlaceCBBlockC2SPacket::handle).add();

        net.messageBuilder(PlayerActionsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerActionsC2SPacket::new)
                .encoder(PlayerActionsC2SPacket::encode)
                .consumerMainThread(PlayerActionsC2SPacket::handle).add();

        INSTANCE = net;
    }


    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

}
