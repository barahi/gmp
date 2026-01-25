package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class GameSettingsJson {
    @JsonProperty
    @NotNull(message = "playerCount is required")
    @Min(value = 2, message = "playerCount must be at least 2")
    private int playerCount;

    @JsonProperty
    @NotNull(message = "categories is required")
    @NotEmpty(message = "categories must not be empty")
    private String[] categories;

    @JsonProperty
    @NotNull(message = "roundDuration is required")
    @Min(value = 1, message = "roundDuration must be at least 1")
    private int roundDuration;

    @JsonProperty
    @NotNull(message = "numberOfRounds is required")
    @Min(value = 1, message = "numberOfRounds must be at least 1")
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
