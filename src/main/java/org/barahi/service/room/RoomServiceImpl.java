package org.barahi.service.room;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.barahi.infra.exceptions.RoomAuthenticationException;
import org.barahi.server.json.JoinRoomJson;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.infra.exceptions.RoomFullException;
import org.barahi.server.json.RoomCreateJson;
import org.barahi.server.json.RoomJson;
import org.barahi.server.serializer.RoomSerializer;
import org.barahi.serviceapi.gameSettings.GameSettings;
import org.barahi.serviceapi.gameSettings.GameSettingsImpl;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerService;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomImpl;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.PlayerStore;
import org.barahi.store.RoomStore;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

public class RoomServiceImpl implements RoomService {

    private final PlayerService playerService;
    private final RoomStore roomStore;
    private final RoomSerializer roomSerializer;
    private final GameSettingsStore gameSettingsStore;
    private final PlayerStore playerStore;

    @Inject
    public RoomServiceImpl(
            PlayerService playerService,
            RoomStore roomStore,
            RoomSerializer roomSerializer,
            GameSettingsStore gameSettingsStore,
            PlayerStore playerStore
    ) {
        this.playerService = playerService;
        this.roomStore = roomStore;
        this.roomSerializer = roomSerializer;
        this.gameSettingsStore = gameSettingsStore;
        this.playerStore = playerStore;
    }

    @Override
    public RoomJson createRoom(RoomCreateJson createJson) throws ObjectNotFoundException {
        // validate host exists
        Player host = playerService.getPlayer(PlayerId.of(createJson.getHostPlayerId()));

        // create room domain
        UUID roomId = UUID.randomUUID();
        UUID gameSettingsId = UUID.randomUUID();
        Room room = new RoomImpl(new RoomId(roomId), host.getId(), false, Instant.now());
        GameSettings settings = new GameSettingsImpl(
                new GameSettings.GameSettingsId(gameSettingsId),
                room.getId(),
                createJson.getMaxPlayers(),
                createJson.getRoundDuration(),
                createJson.getNumberOfRounds(),
                createJson.getCategories(),
                createJson.getExcludedLetters(),
                createJson.getLanguage(),
                createJson.getPassword()
        );

        // persist room and add host as participant
        try {
            roomStore.createRoom(room);
            roomStore.addPlayerToRoom(room.getId(), host.getId());
            gameSettingsStore.createGameSettings(settings);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create room", e);
        }

        return roomSerializer.toJson(room, settings);
    }

    @Override
    public RoomDto getRoomSettings(RoomId roomId){
        return roomStore.getRoomSettings(roomId);
    }

    @Override
    public boolean isPlayerInRoom(RoomId roomId, PlayerId playerId){
        return roomStore.isPlayerInRoom(roomId, playerId);
    }

    @Override
    public void addPlayerToRoom(String roomId, JoinRoomJson joinRoomJson) throws RoomFullException, IllegalArgumentException, RoomAuthenticationException {
        PlayerId playerId;
        RoomId roomUUID;
        roomUUID = Room.RoomId.of(roomId);
        Integer currentPlayersInRoom = roomStore.getCurrentPlayersInRoomCount(roomUUID);
        Integer maxPlayersForRoom = gameSettingsStore.getMaxPlayers(roomUUID);
        if (Objects.equals(currentPlayersInRoom, maxPlayersForRoom)) {
            throw new RoomFullException();
        }

        try {
            playerId = new Player.PlayerId(UUID.fromString(joinRoomJson.getPlayerId()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Invalid Id Format %s ", e));
        }

        Room room;
        Player player;
        try {
            room = roomStore.getRoomWithId(roomUUID);
            player = playerService.getPlayer(playerId);
            System.out.println("got player: " + player);
        } catch (ObjectNotFoundException e){
            throw new NotFoundException("Could not find player with id: " + playerId);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("Invalid Resource %s", e));
        }

        String providedPassword = joinRoomJson.getPassword() == null? "" : joinRoomJson.getPassword().trim();

        if (gameSettingsStore.requiresPassword(roomUUID)) {
            String password = gameSettingsStore.getPasswordByRoomId(room.getId());
            if (providedPassword.isEmpty()){
                throw new RoomAuthenticationException("Room requires password");
            }
            if (!password.equals(providedPassword)){
                throw new RoomAuthenticationException("Password does not match");
            }
        }
        boolean checkPlayer = isPlayerInRoom(roomUUID, playerId);
        if (checkPlayer ){
            System.out.println("player in room already");
            return;
        }

        // add player to the room
        roomStore.addPlayerToRoom(roomUUID, playerId);
    }

    @Override
    public List<PlayerId> getPlayerIdsInRoom(RoomId roomId){
        return roomStore.getPlayerIdsInRoom(roomId);
    }

    @Override
    public List<Player> getPlayersInRoom(RoomId roomId) {
        return playerStore.getPlayersInRoom(roomId);
    }

    @Override
    public RoomId getRoomIdForPlayer(PlayerId playerId) {
        return playerStore.getRoomIdForPlayer(playerId);
    }

    
    @Override
    public void removeRoomAndAllItsResources(String roomId) {
        List<PlayerId> playerIds = roomStore.getPlayerIdsInRoom(Room.RoomId.of(roomId));
        roomStore.deleteRoom(Room.RoomId.of(roomId));

        // delete all players in room
        playerStore.deletePlayer(playerIds);
    }
}
