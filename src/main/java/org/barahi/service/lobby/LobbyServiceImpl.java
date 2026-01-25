package org.barahi.service.lobby;

import java.util.UUID;

import org.barahi.service.room.RoomServiceImpl;
import org.barahi.serviceapi.gamesettings.GameSettings;
import org.barahi.serviceapi.gamesettings.GameSettingsService;
import org.barahi.serviceapi.lobby.Lobby;
import org.barahi.serviceapi.lobby.LobbyImpl;
import org.barahi.serviceapi.lobby.LobbyService;
import org.barahi.store.LobbyStore;

import jakarta.inject.Inject;

public class LobbyServiceImpl implements LobbyService {
    private final LobbyStore lobbyStore;
    private final GameSettingsService gameSettingsService;
    private final RoomServiceImpl roomService;

    @Inject
    public LobbyServiceImpl(LobbyStore lobbyStore, GameSettingsService gameSettingsService, RoomServiceImpl roomService) {
        this.lobbyStore = lobbyStore;
        this.gameSettingsService = gameSettingsService;
        this.roomService = roomService;
    }

    @Override
    public Lobby getLobby(Lobby.LobbyId id) throws IllegalAccessException {
        return lobbyStore.getLobby(id);
    }

    @Override
    public Lobby storeLobby(Lobby unsavedLobby) throws IllegalAccessException {
        String lobbyId = UUID.randomUUID().toString();
        
        Lobby lobbyWithIds = new LobbyImpl(
            lobbyId,
            unsavedLobby.getGameSettingsId(),
            new String[]{},
            false
        );
        
        Lobby savedLobby = lobbyStore.storeLobby(lobbyWithIds);
        
        try {
            GameSettings gameSettings = gameSettingsService.getGameSettings(
                new GameSettings.GameSettingsId(unsavedLobby.getGameSettingsId())
            );
            
            roomService.createRoomFromLobby(
                UUID.randomUUID(),
                new UUID[]{},
                gameSettings.getPlayerCount(),
                gameSettings.getNumberOfRounds(),
                // FIX: language should be dynamic and should be passed from lobby creation request
                // For now, we hardcode it to "en" -> english
                "en"
            );
        } catch (IllegalAccessException e) {
            throw e;
        }
        
        return savedLobby;
    }

    @Override
    public void removeLobby(Lobby.LobbyId id) {
        lobbyStore.removeLobby(id);
    }
}