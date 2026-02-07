package org.barahi.serviceapi.room;

import org.barahi.server.json.JoinRoomJson;
import org.barahi.server.json.RoomCreateJson;
import org.barahi.server.json.RoomJson;

public interface RoomService {
    RoomJson createRoom(RoomCreateJson createJson) throws IllegalArgumentException;
    void addPlayerToRoom(String roomId, JoinRoomJson joinRoomJson) throws IllegalArgumentException;
}
