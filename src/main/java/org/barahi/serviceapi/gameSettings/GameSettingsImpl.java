package org.barahi.serviceapi.gameSettings;

import org.barahi.serviceapi.room.Room;


public class GameSettingsImpl implements GameSettings {
    private final GameSettingsId id;
    private final Room.RoomId roomId;
    private final int maxPlayers;
    private final int roundDuration;
    private final int numberOfRounds;
    private final String language;
    private final String password;

    public GameSettingsImpl(GameSettingsId id, Room.RoomId roomId, int maxPlayers, int roundDuration, int numberOfRounds, String language, String password) {
        this.id = id;
        this.roomId = roomId;
        this.maxPlayers = maxPlayers;
        this.roundDuration = roundDuration;
        this.numberOfRounds = numberOfRounds;
        this.language = language;
        this.password = password;
    }

    @Override
    public GameSettingsId getId() {
        return id;
    }

    @Override
    public Room.RoomId getRoomId() {
        return roomId;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
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
    public String getLanguage() {
        return language;
    }

    @Override
    public String getPassword() {
        return password;
    }
}