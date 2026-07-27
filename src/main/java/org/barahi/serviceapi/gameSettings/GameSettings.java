package org.barahi.serviceapi.gameSettings;

import java.util.List;
import java.util.UUID;

import org.barahi.infra.TypedUUID;
import org.barahi.serviceapi.room.Room;

public interface GameSettings {
    GameSettingsId getId();
    Room.RoomId getRoomId();
    int getMaxPlayers();
    int getRoundDuration();
    int getNumberOfRounds();
    List<String> getCategories();
    List<String> getExcludedLetters();
    String getLanguage();
    String getPassword();

    class GameSettingsId extends TypedUUID<GameSettings> {
        public GameSettingsId(java.util.UUID id) {
            super(id);
        }
        public static GameSettingsId of(UUID id) {
            return new GameSettingsId(id);
        }
        public static GameSettingsId of(String id) throws IllegalArgumentException {
            return GameSettingsId.of(UUID.fromString(id));
        }

        @Override
        public String toString() {
            return this.getId().toString();
        }
    }
}
