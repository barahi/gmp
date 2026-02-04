package org.barahi.serviceapi.room;

import org.barahi.infra.TypedUUID;
import org.barahi.serviceapi.player.Player;

import java.time.Instant;

public interface Room {
    RoomId getId();
    Player.PlayerId getHostPlayerId();
    boolean isGameStarted();
    Instant getCreatedAt();

    class RoomId extends TypedUUID<Room> {
        public RoomId(java.util.UUID id) {
            super(id);
        }
    }
}
