package org.barahi.serviceapi.lobby;

public interface Lobby {
    String getId();
    String getGameSettingsId();
    String[] getPlayerIds();
    boolean isGameStarted();

    class LobbyId {
        private final String id;

        public LobbyId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}