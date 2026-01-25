package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LobbyJson {
    @JsonProperty
    private String id;

    @JsonProperty
    private String gameSettingsId;

    @JsonProperty
    private String[] playerIds;

    @JsonProperty
    private boolean gameStarted;

    public String getId() {
        return id;
    }

    public LobbyJson setId(String id) {
        this.id = id;
        return this;
    }

    public String getGameSettingsId() {
        return gameSettingsId;
    }

    public LobbyJson setGameSettingsId(String gameSettingsId) {
        this.gameSettingsId = gameSettingsId;
        return this;
    }

    public String[] getPlayerIds() {
        return playerIds;
    }

    public LobbyJson setPlayerIds(String[] playerIds) {
        this.playerIds = playerIds;
        return this;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public LobbyJson setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
        return this;
    }
}