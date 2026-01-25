package org.barahi.serviceapi.gamesettings;

public interface GameSettings {
    String getId();
    String getRoomId();
    int getPlayerCount();
    String[] getCategories();
    int getRoundDuration();
    int getNumberOfRounds();
    String getPassword(); // empty means no password
    String[] getExcludedLetters(); // empty means no exclusions

    class GameSettingsId {
        private final String id;

        public GameSettingsId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
