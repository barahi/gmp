package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomJson {
    @JsonProperty("id")
    private String id;

    @JsonProperty("host_player_id")
    private String hostPlayerId;

    @JsonProperty("is_game_started")
    private boolean isGameStarted;

    public String getId() {
        return id;
    }

    public RoomJson setId(String id) {
        this.id = id;
        return this;
    }

    public String getHostPlayerId() {
        return hostPlayerId;
    }

    public RoomJson setHostPlayerId(String hostPlayerId) {
        this.hostPlayerId = hostPlayerId;
        return this;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public RoomJson setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
        return this;
    }
}
