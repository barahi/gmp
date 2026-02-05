package org.barahi.serviceapi.room;

import org.barahi.infra.TypedUUID;
import org.barahi.serviceapi.player.Player;

import java.time.Instant;
import java.util.UUID;

public interface Room {
    RoomId getId();
    Player.PlayerId getHostPlayerId();
    boolean isGameStarted();
    Instant getCreatedAt();

    class RoomId extends TypedUUID<Room> {
        public RoomId(UUID id) {
            super(id);
        }
    }
}
