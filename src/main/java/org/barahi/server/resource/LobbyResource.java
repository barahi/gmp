package org.barahi.server.resource;

import org.barahi.server.json.GameSettingsJson;
import org.barahi.server.json.LobbyJson;
import org.barahi.server.serializer.GameSettingsSerializer;
import org.barahi.server.serializer.LobbySerializer;
import org.barahi.serviceapi.gamesettings.GameSettings;
import org.barahi.serviceapi.gamesettings.GameSettingsService;
import org.barahi.serviceapi.lobby.Lobby;
import org.barahi.serviceapi.lobby.LobbyService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;


@Path(LobbyResource.BASE_PATH)
public class LobbyResource {
    public static final String BASE_PATH = "lobby";

    private final GameSettingsService gameSettingsService;
    private final GameSettingsSerializer gameSettingsSerializer;
    private final LobbyService lobbyService;
    private final LobbySerializer lobbySerializer;

    @Inject
    public LobbyResource(
            GameSettingsService gameSettingsService,
            GameSettingsSerializer gameSettingsSerializer,
            LobbyService lobbyService,
            LobbySerializer lobbySerializer
    ) {
        this.gameSettingsService = gameSettingsService;
        this.gameSettingsSerializer = gameSettingsSerializer;
        this.lobbyService = lobbyService;
        this.lobbySerializer = lobbySerializer;
    }
    
    @POST
    public LobbyJson createLobby(@Valid GameSettingsJson gameSettingsJson) throws IllegalAccessException {
        GameSettings unsavedGameSettings = gameSettingsSerializer.fromJson(gameSettingsJson);
        GameSettings gameSettings = gameSettingsService.storeGameSettings(unsavedGameSettings);
        
        Lobby unsavedLobby = lobbySerializer.fromJson(new LobbyJson().setGameSettingsId(gameSettings.getId()));
        Lobby lobby = lobbyService.storeLobby(unsavedLobby);
        
        return lobbySerializer.toJson(lobby);
    }
}
