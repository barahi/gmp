package org.barahi.serviceapi.gamesettings;

public class GameSettingsImpl implements GameSettings {
    private final String id;
    private final String roomId;
    private final int playerCount;
    private final String[] categories;
    private final int roundDuration;
    private final int numberOfRounds;
    private final String password;
    private final String[] excludedLetters;

    public GameSettingsImpl(String id, String roomId, int playerCount, String[] categories, int roundDuration, int numberOfRounds, String password, String[] excludedLetters) {
        this.id = id;
        this.roomId = roomId;
        this.playerCount = playerCount;
        this.categories = categories;
        this.roundDuration = roundDuration;
        this.numberOfRounds = numberOfRounds;
        this.password = password;
        this.excludedLetters = excludedLetters;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRoomId() {
        return roomId;
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public String[] getCategories() {
        return categories;
    }

    @Override
    public int getRoundDuration() {
        return roundDuration;
    }

    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String[] getExcludedLetters() {
        return excludedLetters;
    }
}
