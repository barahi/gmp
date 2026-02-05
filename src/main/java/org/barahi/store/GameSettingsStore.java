package org.barahi.store;

import static org.barahi.generated.Tables.CATEGORIES;
import static org.barahi.generated.Tables.EXCLUDED_LETTERS;
import static org.barahi.generated.Tables.GAME_SETTINGS;
import org.barahi.generated.tables.records.GameSettingsRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.gameSettings.GameSettings;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import jakarta.inject.Inject;

public class GameSettingsStore {
    private final DSLContext db;

    @Inject
    public GameSettingsStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public void createGameSettings(GameSettings settings) {
        db.transaction(cfg -> {
            DSLContext tx = DSL.using(cfg);

            tx.insertInto(GAME_SETTINGS)
                    .set(toRecord(settings))
                    .execute();

            for (String category : settings.getCategories()) {
                tx.insertInto(CATEGORIES)
                        .set(CATEGORIES.GAME_SETTINGS_ID, settings.getId().getId().toString())
                        .set(CATEGORIES.CATEGORY, category)
                        .execute();
            }

            for (String letter : settings.getExcludedLetters()) {
                tx.insertInto(EXCLUDED_LETTERS)
                        .set(EXCLUDED_LETTERS.GAME_SETTINGS_ID, settings.getId().getId().toString())
                        .set(EXCLUDED_LETTERS.LETTERS, letter)
                        .execute();
            }
        });
    }

    private GameSettingsRecord toRecord(GameSettings settings) {
        GameSettingsRecord record = new GameSettingsRecord();
        record.setId(settings.getId().getId().toString());
        record.setRoomId(settings.getRoomId().getId().toString());
        record.setMaxPlayers(settings.getMaxPlayers());
        record.setRoundDuration(settings.getRoundDuration());
        record.setNumberOfRounds(settings.getNumberOfRounds());
        record.setLanguage(settings.getLanguage());
        record.setPassword(settings.getPassword());
        return record;
    }
}
