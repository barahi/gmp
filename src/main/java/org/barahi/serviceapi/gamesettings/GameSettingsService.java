package org.barahi.serviceapi.gamesettings;

public interface GameSettingsService {
    GameSettings getGameSettings(GameSettings.GameSettingsId id) throws IllegalAccessException;

    GameSettings storeGameSettings(GameSettings unsavedGameSettings);

    void removeGameSettings(GameSettings.GameSettingsId id);
}
