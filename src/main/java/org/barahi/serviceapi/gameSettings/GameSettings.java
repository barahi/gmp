package org.barahi.serviceapi.gameSettings;

import java.util.List;

import org.barahi.infra.TypedUUID;
import org.barahi.serviceapi.room.Room;

public interface GameSettings {
    GameSettingsId getId();
    Room.RoomId getRoomId();
    int getMaxPlayers();
    int getRoundDuration();
    int getNumberOfRounds();
    String getLanguage();
    String getPassword();
    List<String> getCategories();
    List<String> getExcludedLetters();

    class GameSettingsId extends TypedUUID<GameSettings> {
        public GameSettingsId(java.util.UUID id) {
            super(id);
        }
    }
}
