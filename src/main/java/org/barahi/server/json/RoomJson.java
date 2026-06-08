package org.barahi.server.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomJson {
    @JsonProperty
    private String id;

    @JsonProperty
    private String hostPlayerId;

    @JsonProperty
    private int maxPlayers;

    @JsonProperty
    private int roundDuration;

    @JsonProperty
    private int numberOfRounds;

    @JsonProperty
    private String password;

    @JsonProperty
    private List<String> categories;

    @JsonProperty
    private List<String> excludedLetters;

    @JsonProperty
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

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getExcludedLetters() {
        return excludedLetters;
    }

    public RoomJson setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }

    public RoomJson setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
        return this;
    }

    public RoomJson setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
        return this;
    }

    public RoomJson setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public RoomJson setExcludedLetters(List<String> excludedLetters) {              
        this.excludedLetters = excludedLetters;
        return this;
    }

    public RoomJson setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
        return this;
    }
}
