package org.barahi.serviceapi.room;

import java.time.Instant;
import java.util.UUID;

public class RoomImpl implements Room {
    private final UUID id;
    private final UUID hostPlayerId;
    private final UUID[] playerIds;
    private final Integer maxPlayers;
    private final Integer roundCount;
    private final String language;
    private final RoomStatus status;
    private final Integer currentRound;
    private final Instant createdAt;
    private final Instant updatedAt;

    public RoomImpl(UUID id, UUID hostPlayerId, UUID[] playerIds, Integer maxPlayers,
                   Integer roundCount, String language, RoomStatus status,
                   Integer currentRound, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.hostPlayerId = hostPlayerId;
        this.playerIds = playerIds;
        this.maxPlayers = maxPlayers;
        this.roundCount = roundCount;
        this.language = language;
        this.status = status;
        this.currentRound = currentRound;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getHostPlayerId() {
        return hostPlayerId;
    }

    @Override
    public UUID[] getPlayerIds() {
        return playerIds;
    }

    @Override
    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public Integer getRoundCount() {
        return roundCount;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public RoomStatus getStatus() {
        return status;
    }

    @Override
    public Integer getCurrentRound() {
        return currentRound;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
