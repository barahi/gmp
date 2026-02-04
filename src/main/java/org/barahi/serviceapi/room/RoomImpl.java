package org.barahi.serviceapi.room;

import org.barahi.serviceapi.player.Player;

import java.time.Instant;

public class RoomImpl implements Room {
    private final RoomId id;
    private final Player.PlayerId hostPlayerId;
    private final boolean isGameStarted;
    private final Instant createdAt;

    public RoomImpl(RoomId id, Player.PlayerId hostPlayerId, boolean isGameStarted, Instant createdAt) {
        this.id = id;
        this.hostPlayerId = hostPlayerId;
        this.isGameStarted = isGameStarted;
        this.createdAt = createdAt;
    }

    @Override
    public RoomId getId() {
        return id;
    }

    @Override
    public Player.PlayerId getHostPlayerId() {
        return hostPlayerId;
    }

    @Override
    public boolean isGameStarted() {
        return isGameStarted;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }
}
