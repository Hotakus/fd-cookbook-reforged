package net.hotakus.fdcookbook.networking.packets;

import com.google.common.collect.Maps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class PlayerActionsC2SPacket extends Packets {

    public enum Action {
        SHIFT_DOWN,
        ALT_DOWN
    }

    private static Map<UUID, Map<Action, Boolean>> playersActions = new HashMap<>();

    public static Map<UUID, Map<Action, Boolean>> getPlayersActions() {
        return playersActions;
    }

    public static boolean getPlayerAction(UUID _player, Action _action) {
        if (playersActions.containsKey(_player)) {
            if (playersActions.get(_player).containsKey(_action)) {
                return playersActions.get(_player).get(_action);
            }
        }
        return false;
    }

    public void addPlayerAction(UUID _player, Map.Entry<Action, Boolean> _action) {
        if (!playersActions.containsKey(_player)) {
            playersActions.put(_player, new HashMap<>() {
                {
                    put(_action.getKey(), _action.getValue());
                }
            });
        } else {
            if (playersActions.get(_player).containsKey(_action.getKey())) {
                playersActions.get(_player).replace(_action.getKey(), _action.getValue());
            } else {
                playersActions.get(_player).put(_action.getKey(), _action.getValue());
            }
        }
    }

    public PlayerActionsC2SPacket() {
        super();
    }

    public PlayerActionsC2SPacket(FriendlyByteBuf buf) {
        playersActions = buf.readMap(Maps::newHashMapWithExpectedSize, FriendlyByteBuf::readUUID,
                (_buf) -> {
                    Map<Action, Boolean> aMap;

                    aMap = _buf.readMap(Maps::newHashMapWithExpectedSize,
                            (__buf) -> Action.values()[__buf.readEnum(Action.class).ordinal()],
                            FriendlyByteBuf::readBoolean);

                    return aMap;
                });
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeMap(playersActions,
                FriendlyByteBuf::writeUUID,
                (_buf, aMap) -> {
                    _buf.writeMap(aMap, FriendlyByteBuf::writeEnum, FriendlyByteBuf::writeBoolean);
                }
        );
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            UUID uuid = player.getUUID();

            if (playersActions.containsKey(uuid)) {
                for (Map.Entry<Action, Boolean> entry : playersActions.get(uuid).entrySet()) {
                    if (entry.getKey() == Action.SHIFT_DOWN) {
                        //player.sendMessage(new TextComponent("Shift " + (entry.getValue() ? "Down" : "Up")),
                        //        player.getUUID());
                    } else if (entry.getKey() == Action.ALT_DOWN) {
                        //player.sendMessage(new TextComponent("Alt " + (entry.getValue() ? "Down" : "Up")),
                        //        player.getUUID());
                    }
                }
            }

        });
        return true;
    }

}
