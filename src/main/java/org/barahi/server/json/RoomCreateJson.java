package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class RoomCreateJson {

    @NotNull
    private UUID hostPlayerId;

    @Min(1)
    private int maxPlayers;

    @Min(1)
    private int roundDuration;

    @Min(1)
    private int numberOfRounds;

    @NotNull
    @NotEmpty
    private List<String> categories;

    private List<String> excludedLetters;

    @NotNull
    @NotEmpty
    private String language;

    @NotEmpty
    private String password;

    public UUID getHostPlayerId() {
        return hostPlayerId;
    }

    public void setHostPlayerId(UUID hostPlayerId) {
        this.hostPlayerId = hostPlayerId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayesr(int maxPlayer) {
        this.maxPlayers = maxPlayer;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getExcludedLetters() {
        return excludedLetters;
    }

    public void setExcludedLetters(List<String> excludedLetters) {
        this.excludedLetters = excludedLetters;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
