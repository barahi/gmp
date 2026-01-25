package org.barahi.serviceapi.room;

import java.time.Instant;
import java.util.UUID;

public interface Room {
    UUID getId();

    UUID getHostPlayerId();

    UUID[] getPlayerIds();

    Integer getMaxPlayers();

    Integer getRoundCount();

    String getLanguage();

    RoomStatus getStatus();

    Integer getCurrentRound();

    Instant getCreatedAt();

    Instant getUpdatedAt();

    enum RoomStatus {
        WAITING, IN_GAME, COMPLETED, CLOSED
    }

    class RoomId {
        private final UUID id;

        public RoomId(UUID id) {
            this.id = id;
        }

        public UUID getId() {
            return id;
        }

        @Override
        public String toString() {
            return id.toString();
        }
    }
}
