package org.barahi.serviceapi.lobby;

public interface LobbyService {
    Lobby getLobby(Lobby.LobbyId id) throws IllegalAccessException;

    Lobby storeLobby(Lobby unsavedLobby) throws IllegalAccessException;

    void removeLobby(Lobby.LobbyId id);
}
