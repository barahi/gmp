package org.barahi.service.gamesettings;

import java.util.UUID;

import org.barahi.serviceapi.gamesettings.GameSettings;
import org.barahi.serviceapi.gamesettings.GameSettingsImpl;
import org.barahi.serviceapi.gamesettings.GameSettingsService;
import org.barahi.store.GameSettingsStore;

import jakarta.inject.Inject;

public class GameSettingsServiceImpl implements GameSettingsService {
    private final GameSettingsStore gameSettingsStore;

    @Inject
    public GameSettingsServiceImpl(GameSettingsStore gameSettingsStore) {
        this.gameSettingsStore = gameSettingsStore;
    }

    @Override
    public GameSettings getGameSettings(GameSettings.GameSettingsId id) throws IllegalAccessException {
        return gameSettingsStore.getGameSettings(id);
    }

    @Override
    public GameSettings storeGameSettings(GameSettings unsavedGameSettings) {
        // Generate UUID for the game settings
        String gameSettingsId = UUID.randomUUID().toString();
        
        // Set room_id (in your case, same as settings id, but adjust based on your logic)
        String roomId = UUID.randomUUID().toString();
        
        GameSettings gameSettingsWithIds = new GameSettingsImpl(
            gameSettingsId,
            roomId,
            unsavedGameSettings.getPlayerCount(),
            unsavedGameSettings.getCategories(),
            unsavedGameSettings.getRoundDuration(),
            unsavedGameSettings.getNumberOfRounds(),
            unsavedGameSettings.getPassword(),
            unsavedGameSettings.getExcludedLetters()
        );
        
        // Store in database
        GameSettings savedGameSettings = gameSettingsStore.storeGameSettings(gameSettingsWithIds);
        return savedGameSettings;
    }

    @Override
    public void removeGameSettings(GameSettings.GameSettingsId id) {
        gameSettingsStore.removeGameSettings(id);
    }
}
