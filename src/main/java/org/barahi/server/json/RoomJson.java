package org.barahi.server.json;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomJson {
    @JsonProperty
    private UUID hostPlayerId;

    @JsonProperty
    private UUID[] playerIds;

    @JsonProperty
    private Integer maxPlayers;

    @JsonProperty
    private Integer roundCount;

    @JsonProperty
    private String language;

    @JsonProperty
    private String status;

    @JsonProperty
    private Integer currentRound;

    public RoomJson() {
    }

    public RoomJson(UUID hostPlayerId, UUID[] playerIds, Integer maxPlayers,
                    Integer roundCount, String language, String status, Integer currentRound) {
        this.hostPlayerId = hostPlayerId;
        this.playerIds = playerIds;
        this.maxPlayers = maxPlayers;
        this.roundCount = roundCount;
        this.language = language;
        this.status = status;
        this.currentRound = currentRound;
    }

    public UUID getHostPlayerId() {
        return hostPlayerId;
    }

    public UUID[] getPlayerIds() {
        return playerIds;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Integer getRoundCount() {
        return roundCount;
    }

    public String getLanguage() {
        return language;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setHostPlayerId(UUID hostPlayerId) {
        this.hostPlayerId = hostPlayerId;
    }

    public void setPlayerIds(UUID[] playerIds) {
        this.playerIds = playerIds;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setRoundCount(Integer roundCount) {
        this.roundCount = roundCount;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }
}
