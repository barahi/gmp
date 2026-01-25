package org.barahi.service.lobby;

import java.util.UUID;

import org.barahi.serviceapi.gamesettings.GameSettingsService;
import org.barahi.serviceapi.lobby.Lobby;
import org.barahi.serviceapi.lobby.LobbyImpl;
import org.barahi.serviceapi.lobby.LobbyService;
import org.barahi.store.LobbyStore;

import jakarta.inject.Inject;

public class LobbyServiceImpl implements LobbyService {
    private final LobbyStore lobbyStore;

    @Inject
    public LobbyServiceImpl(LobbyStore lobbyStore, GameSettingsService gameSettingsService) {
        this.lobbyStore = lobbyStore;
    }

    @Override
    public Lobby getLobby(Lobby.LobbyId id) throws IllegalAccessException {
        return lobbyStore.getLobby(id);
    }

    @Override
    public Lobby storeLobby(Lobby unsavedLobby) {
        // Generate UUID for the lobby
        String lobbyId = UUID.randomUUID().toString();
        
        // Create Lobby with gameSettingsId, empty playerIds, and gameStarted=false
        Lobby lobbyWithIds = new LobbyImpl(
            lobbyId,
            unsavedLobby.getGameSettingsId(),
            new String[]{},
            false
        );
        
        // Store in database
        Lobby savedLobby = lobbyStore.storeLobby(lobbyWithIds);
        return savedLobby;
    }

    @Override
    public void removeLobby(Lobby.LobbyId id) {
        lobbyStore.removeLobby(id);
    }
}