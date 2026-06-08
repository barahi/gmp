package org.barahi.store;

import static org.barahi.generated.Tables.CATEGORIES;
import static org.barahi.generated.Tables.EXCLUDED_LETTER;
import static org.barahi.generated.Tables.GAME_SETTINGS;


import org.barahi.generated.tables.records.GameSettingsRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.gameSettings.GameSettings;
import org.barahi.serviceapi.gameSettings.GameSettings.GameSettingsId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import jakarta.inject.Inject;
import javassist.tools.rmi.ObjectNotFoundException;

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

            String formattedId = settings.getId().toString();
            tx.insertInto(GAME_SETTINGS)
              .set(toRecord(settings))
              .set(GAME_SETTINGS.ID, formattedId)
              .execute();

            for (String letter : settings.getExcludedLetters()) {
                tx.insertInto(EXCLUDED_LETTER)
                  .set(EXCLUDED_LETTER.GAME_SETTINGS_ID, formattedId)
                  .set(EXCLUDED_LETTER.LETTER, letter)
                  .execute();
            }

            for (String category : settings.getCategories()) {
                CategoryId categoryId = new CategoryId(UUID.randomUUID());
                createCategory(tx, categoryId, formattedId, category);
            }

    });
}

    public void createCategory(DSLContext tx, CategoryId categoryId, String gameSettingsId, String category) {
        tx.insertInto(CATEGORIES)
          .set(CATEGORIES.ID, categoryId.getId().toString())
          .set(CATEGORIES.GAME_SETTINGS_ID, gameSettingsId)
          .set(CATEGORIES.CATEGORY, category)
          .execute();
    }


    public GameSettingsId getGameSettingsId(RoomId roomId) {
        String id = db.select(GAME_SETTINGS.ID)
          .from(GAME_SETTINGS)
          .where(GAME_SETTINGS.ROOM_ID.eq(roomId.getId().toString()))
          .fetchOne(GAME_SETTINGS.ID);
        return GameSettingsId.of(id);
    }

    public String getPasswordByRoomId(RoomId roomId) {
        return db.select(GAME_SETTINGS.PASSWORD)
          .from(GAME_SETTINGS)
          .where(GAME_SETTINGS.ROOM_ID.eq(roomId.getId().toString()))
          .fetchOneInto(String.class);
    }

    public Integer getNumberOfRounds(RoomId roomId) {
        return db.select(GAME_SETTINGS.NUMBER_OF_ROUNDS)
          .from(GAME_SETTINGS)
          .where(GAME_SETTINGS.ROOM_ID.eq(roomId.getId().toString()))
          .fetchOne(GAME_SETTINGS.NUMBER_OF_ROUNDS);
    }

    public List<Character> getLetterExclusions(GameSettingsId gameSettingsId) {
        return db.select(EXCLUDED_LETTER.LETTER)
          .from(EXCLUDED_LETTER)
          .where(EXCLUDED_LETTER.GAME_SETTINGS_ID.eq(gameSettingsId.getId().toString()))
          .fetch()
          .map(r -> r.get(EXCLUDED_LETTER.LETTER).charAt(0));
    }

    public Integer getMaxPlayers(RoomId roomId) {
        return db.select(GAME_SETTINGS.MAX_PLAYERS)
          .from(GAME_SETTINGS)
          .where(GAME_SETTINGS.ROOM_ID.eq(roomId.getId().toString()))
          .fetchOne(GAME_SETTINGS.MAX_PLAYERS);
    }

    public void addNewLetterToExcludedLetters(GameSettingsId gameSettingsId, char c){
        db.insertInto(EXCLUDED_LETTER)
          .set(EXCLUDED_LETTER.GAME_SETTINGS_ID, gameSettingsId.getId().toString())
          .set(EXCLUDED_LETTER.LETTER, String.valueOf(c))
          .execute();
    }

    public CategoryId getCategoryIdFromName(String categoryName){
        String categoryId = db.select(CATEGORIES.ID)
          .from(CATEGORIES)
          .where(CATEGORIES.CATEGORY.eq(categoryName))
          .fetchOne(CATEGORIES.ID);
        return CategoryId.of(categoryId);
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
