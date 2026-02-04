package org.barahi.server.serializer;

import org.barahi.server.json.RoomJson;
import org.barahi.serviceapi.room.Room;

public class RoomSerializer {

    public RoomJson toJson(Room room) {
        return new RoomJson()
                .setId(room.getId().getId().toString())
                .setHostPlayerId(room.getHostPlayerId().getId().toString())
                .setGameStarted(room.isGameStarted());
    }
}
