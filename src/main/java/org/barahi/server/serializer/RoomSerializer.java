package org.barahi.server.serializer;

import org.barahi.server.json.RoomJson;
import org.barahi.serviceapi.room.Room;

import jakarta.inject.Inject;

public class RoomSerializer {

    @Inject
    public RoomSerializer() {
    }

    public RoomJson toJson(Room room) {
        return new RoomJson(
                room.getHostPlayerId(),
                room.getPlayerIds(),
                room.getMaxPlayers(),
                room.getRoundCount(),
                room.getLanguage(),
                room.getStatus().toString(),
                room.getCurrentRound()
        );
    }

    public Room fromJson(RoomJson roomJson) {
        throw new UnsupportedOperationException("Room could not be created.");
    }
}
