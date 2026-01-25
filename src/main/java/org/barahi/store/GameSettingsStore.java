package org.barahi.store;

import static org.barahi.generated.Tables.GAME_SETTINGS;
import org.barahi.generated.tables.records.GameSettingsRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.gamesettings.GameSettings;
import org.barahi.serviceapi.gamesettings.GameSettingsImpl;
import org.jooq.DSLContext;

import jakarta.inject.Inject;

public class GameSettingsStore {
    private final DSLContext db;

    @Inject
    public GameSettingsStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public GameSettings getGameSettings(GameSettings.GameSettingsId id) throws IllegalAccessException {
        GameSettingsRecord record = db.selectFrom(GAME_SETTINGS)
                .where(GAME_SETTINGS.ID.eq(id.getId()))
                .fetchOne();
        if (record == null) {
            throw new IllegalAccessException(String.format("Tried to access non existing game settings with id: %s", id));
        }
        return fromRecord(record);
    }

    public GameSettings storeGameSettings(GameSettings gameSettings) {
        GameSettingsRecord record = toRecord(gameSettings);
        db.insertInto(GAME_SETTINGS)
          .set(record)
          .execute();
        return gameSettings;
    }

    public void removeGameSettings(GameSettings.GameSettingsId id) {
        db.delete(GAME_SETTINGS)
           .where(GAME_SETTINGS.ID.eq(id.getId()))
           .execute();
    }

    private GameSettingsRecord toRecord(GameSettings gameSettings) {
        GameSettingsRecord record = new GameSettingsRecord();
        record.setId(gameSettings.getId());
        record.setRoomId(gameSettings.getRoomId());
        record.setPlayerCount(gameSettings.getPlayerCount());
        record.setCategories(gameSettings.getCategories());
        record.setRoundDuration(gameSettings.getRoundDuration());
        record.setNumberOfRounds(gameSettings.getNumberOfRounds());
        record.setPassword(gameSettings.getPassword());
        record.setExcludedLetters(gameSettings.getExcludedLetters());
        return record;
    }

    private GameSettings fromRecord(GameSettingsRecord record) {
        return new GameSettingsImpl(
                record.getId(),
                record.getRoomId(),
                record.getPlayerCount(),
                record.getCategories(),
                record.getRoundDuration(),
                record.getNumberOfRounds(),
                record.getPassword(),
                record.getExcludedLetters()
        );
    }
}
