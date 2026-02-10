package org.barahi.store;

import static org.barahi.generated.Tables.CATEGORIES;
import static org.barahi.generated.Tables.EXCLUDED_LETTER;
import static org.barahi.generated.Tables.GAME_SETTINGS;


import org.barahi.generated.tables.pojos.Room;
import org.barahi.generated.tables.records.GameSettingsRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.gameSettings.GameSettings;
import org.barahi.serviceapi.gameSettings.GameSettings.GameSettingsId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

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

            for (String letter : settings.getExcludedLetters()) {
                tx.insertInto(EXCLUDED_LETTER)
                        .set(CATEGORIES.GAME_SETTINGS_ID, settings.getId().getId().toString())
                        .set(EXCLUDED_LETTER.LETTER, letter)
                        .execute();
            }
        });

    }

//    public void createCategory(CategoryId categoryId, UUID gameSettingsId, String category) {
//        db.insertInto(CATEGORIES)
//          .set(CATEGORIES.ID, categoryId.toString())
//          .set(CATEGORIES.GAME_SETTINGS_ID, gameSettingsId.toString())
//          .set(CATEGORIES.CATEGORY, category)
//          .execute();
//    }

    public String getGameSettingsId(RoomId roomId){
        return db.select(GAME_SETTINGS.ROOM_ID)
          .from(GAME_SETTINGS)
          .where(GAME_SETTINGS.ROOM_ID.eq(roomId.getId().toString()))
          .fetchOne(GAME_SETTINGS.ID);
    }

    public String getPasswordByRoomId(RoomId roomId) {
        return db.select(GAME_SETTINGS.PASSWORD)
                .from(GAME_SETTINGS)
                .where(GAME_SETTINGS.ROOM_ID.eq(roomId.getId().toString()))
                .fetchOneInto(String.class);
    }

    public Integer getNumberOfRounds(RoomId roomId){
        return db.select(GAME_SETTINGS.NUMBER_OF_ROUNDS)
          .from(GAME_SETTINGS)
          .where(GAME_SETTINGS.ROOM_ID.eq(roomId.toString()))
          .fetchOne(GAME_SETTINGS.NUMBER_OF_ROUNDS);
    }

    public List<Character> getLetterExclusions(RoomId roomId){
        return db.select(EXCLUDED_LETTER.LETTER)
          .from(EXCLUDED_LETTER)
          .where(EXCLUDED_LETTER.GAME_SETTINGS_ID.eq(roomId.toString()))
          .fetch()
          .map(r -> r.get(EXCLUDED_LETTER.LETTER).charAt(0));
    }

    private GameSettingsRecord toRecord(GameSettings settings) {
        GameSettingsRecord record = new GameSettingsRecord();
        record.setRoomId(settings.getRoomId().getId().toString());
        record.setMaxPlayers(settings.getMaxPlayers());
        record.setRoundDuration(settings.getRoundDuration());
        record.setNumberOfRounds(settings.getNumberOfRounds());
        record.setLanguage(settings.getLanguage());
        record.setPassword(settings.getPassword());
        return record;
    }
}
