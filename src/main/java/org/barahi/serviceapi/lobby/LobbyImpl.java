package org.barahi.serviceapi.lobby;

public class LobbyImpl implements Lobby {
    private final String id;
    private final String gameSettingsId;
    private final String[] playerIds;
    private final boolean gameStarted;

    public LobbyImpl(String id, String gameSettingsId, String[] playerIds, boolean gameStarted) {
        this.id = id;
        this.gameSettingsId = gameSettingsId;
        this.playerIds = playerIds;
        this.gameStarted = gameStarted;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getGameSettingsId() {
        return gameSettingsId;
    }

    @Override
    public String[] getPlayerIds() {
        return playerIds;
    }

    @Override
    public boolean isGameStarted() {
        return gameStarted;
    }
}