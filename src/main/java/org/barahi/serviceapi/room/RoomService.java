package org.barahi.serviceapi.room;

import org.barahi.server.json.JoinRoomJson;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.server.json.RoomCreateJson;
import org.barahi.server.json.RoomJson;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;

public interface RoomService {
    RoomJson createRoom(RoomCreateJson createJson) throws ObjectNotFoundException;
    void addPlayerToRoom(String roomId, JoinRoomJson joinRoomJson) throws IllegalArgumentException;

  List<PlayerId> getPlayerIdsInRoom(RoomId roomId);

  List<Player> getPlayersInRoom(RoomId roomId);
    RoomId getRoomIdForPlayer(PlayerId playerId) throws ObjectNotFoundException;
}
