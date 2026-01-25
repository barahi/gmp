package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameSettingsJson {
    @JsonProperty
    private int playerCount;

    @JsonProperty
    private String[] categories;

    @JsonProperty
    private int roundDuration;

    @JsonProperty
    private int numberOfRounds;

    @JsonProperty
    private String password; // empty means no password

    @JsonProperty
    private String[] excludedLetters; // empty means no exclusions

    public int getPlayerCount() {
        return playerCount;
    }

    public GameSettingsJson setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    public String[] getCategories() {
        return categories;
    }

    public GameSettingsJson setCategories(String[] categories) {
        this.categories = categories;
        return this;
    }

    public GameSettingsJson setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
        return this;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public GameSettingsJson setNumberOfRounds (int numberOfRounds){
        this.numberOfRounds = numberOfRounds;
        return this;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public GameSettingsJson setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public GameSettingsJson setExcludedLetters(String[] excludedLetters) {
        this.excludedLetters = excludedLetters;
        return this;
    }

    public String[] getExcludedLetters() {
        return excludedLetters;
    }
}
