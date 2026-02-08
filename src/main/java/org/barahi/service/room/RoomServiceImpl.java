package org.barahi.service.room;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.barahi.server.json.JoinRoomJson;
import org.barahi.infra.exceptions.ObjectNotFoundException;
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
import org.barahi.store.RoomStore;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

public class RoomServiceImpl implements RoomService {

    private final PlayerService playerService;
    private final RoomStore roomStore;
    private final RoomSerializer roomSerializer;
    private final GameSettingsStore gameSettingsStore;

    @Inject
    public RoomServiceImpl(PlayerService playerService, RoomStore roomStore, RoomSerializer roomSerializer, GameSettingsStore gameSettingsStore) {
        this.playerService = playerService;
        this.roomStore = roomStore;
        this.roomSerializer = roomSerializer;
        this.gameSettingsStore = gameSettingsStore;
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
                createJson.getLanguage(),
                createJson.getPassword(),
                createJson.getCategories(),
                createJson.getExcludedLetters()
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
    public void addPlayerToRoom(String roomId, JoinRoomJson joinRoomJson) throws IllegalArgumentException {
        PlayerId playerId;
        RoomId roomUUID;
        try {
            playerId = new Player.PlayerId(UUID.fromString(joinRoomJson.getPlayerId()));
            roomUUID = Room.RoomId.of(roomId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Invalid Id Format %s ", e));
        }

        // check if valid room and player
        Room room;
        Player player;
        try {
            room = roomStore.getRoomWithId(roomUUID);
            player = playerService.getPlayer(playerId);
        } catch (ObjectNotFoundException e){
            throw new NotFoundException("Could not find player with id: " + playerId);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("Invalid Resource %s", e));
        }

        // check if the given password matches the room's password
        if (joinRoomJson.getPassword() != null) {
            String password = gameSettingsStore.getPasswordByRoomId(room.getId());

            if (!password.equals(joinRoomJson.getPassword())) {
                throw new IllegalArgumentException(
                        String.format("The Password Does Not Match For The Given Room ID: %s", roomUUID));
            }
        }

        // add player to the room
        roomStore.addPlayerToRoom(roomUUID, playerId);
    }

    @Override
    public List<Player> getPlayersInRoom(RoomId roomId) {
        throw new UnsupportedOperationException("getPlayersInRoom is not yet implemented");
    }

    @Override
    public RoomId getRoomIdForPlayer(PlayerId playerId) {
        throw new UnsupportedOperationException("getRoomIdForPlayer is not yet implemented");
    }
}
