package org.barahi.serviceapi.room;

import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.server.json.RoomCreateJson;
import org.barahi.server.json.RoomJson;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;

public interface RoomService {
    RoomJson createRoom(RoomCreateJson createJson) throws ObjectNotFoundException;

    List<Player> getPlayersInRoom(RoomId roomId);

    RoomId getRoomIdForPlayer(PlayerId playerId);
}
