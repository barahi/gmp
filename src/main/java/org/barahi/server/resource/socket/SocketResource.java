package org.barahi.server.resource.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.barahi.infra.LoggerFactory;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.server.resource.GuiceWebSocketConfigurator;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerService;

import jakarta.inject.Inject;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/tootiefrootie", configurator = GuiceWebSocketConfigurator.class)
public class SocketResource {
    private static final BiMap<PlayerId, Session> PLAYER_SESSIONS = Maps.synchronizedBiMap(HashBiMap.create());
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.createLogger(SocketResource.class);

    private final PlayerService playerService;
    private final RoomService roomService;

    @Inject
    public SocketResource(PlayerService playerService, RoomService roomService) {
        this.playerService = playerService;
        this.roomService = roomService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("playerId") String rawPlayerId) {
        PlayerId playerId;
        try {
            playerId = PlayerId.of(rawPlayerId);
        } catch (IllegalArgumentException e) {
            broadcastErrorAndCloseSession(session, "Improperly formatted player id: " + rawPlayerId);
            return;
        }
        Player player;
        try {
            player = playerService.getPlayer(playerId);
        } catch (ObjectNotFoundException e) {
            broadcastErrorAndCloseSession(session, "Player not found: " + playerId);
            return;
        }

        if (PLAYER_SESSIONS.containsKey(player.getId())) {
            broadcastErrorAndCloseSession(session, "Player already connected: " + playerId);
            return;
        }
        PLAYER_SESSIONS.put(player.getId(), session);
        RoomId roomId = roomService.getRoomIdForPlayer(playerId);
        List<Player> players = roomService.getPlayersInRoom(roomId);
        broadcastEventToRoom(roomId, PlayerJoinedEvent.withListOfPlayers(players));
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from " + session.getId() + ": " + message);

        PlayerId playerId = PLAYER_SESSIONS.inverse().get(session);
        RoomId roomId = roomService.getRoomIdForPlayer(playerId);

        Event<?> event = getEventFromString(message);
        EventType eventType = EventType.valueOf(event.getType());
        switch (eventType) {
            case START_ROUND: {
                StartRoundEvent startRoundEvent = (StartRoundEvent) event;
                // TODO(michelle): Start round here
                broadcastEventToRoom(roomId, startRoundEvent);
            }
            case SUBMIT_ANSWERS: {
                // TODO(michelle): Submit answers here
            }
            case VOTE_INVALID: {
                // TODO(michelle): Vote invalid here
            }
            case NOOP: {
                // Do Nothing.
            }
            case PLAYER_LEFT:
            case PLAYER_JOINED:
            default: {
                throw new IllegalStateException("Unexpected value: " + event.getType());
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket connection closed: " + session.getId());
    }

    public void broadcastEventToRoom(RoomId roomId, Event<?> event) {
        List<Player> players = roomService.getPlayersInRoom(roomId);
        broadcastEventToRoom(players, event);
    }

    public void broadcastEventToRoom(List<Player> players, Event<?> event) {
        String eventDetails = writeEventAsString(event);
        for (Player player: players) {
            Session session = PLAYER_SESSIONS.get(player.getId());
            try {
                session.getBasicRemote().sendText(eventDetails);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void broadcastError(Session session, String message) {
        LOGGER.severe(message);
        try {
            session.getBasicRemote().sendText("Error: " + message);
        } catch (IOException e) {
            LOGGER.severe("Failed to send error message to " + session.getId() + ": " + e.getMessage());
        }
    }

    private void broadcastErrorAndCloseSession(Session session, String message) {
        broadcastError(session, message);
        try {
            session.close();
        } catch (IOException e) {
            LOGGER.severe("Failed to close session " + session.getId() + ": " + e.getMessage());
        }
    }

    private String writeEventAsString(Event<?> event) {
        try {
            return OBJECT_MAPPER.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            LOGGER.severe("Should not happen.");
            return "";
        }
    }

    private Event<?> getEventFromString(String eventDetails) {
        try {
            return OBJECT_MAPPER.readValue(eventDetails, Event.class);
        } catch (JsonProcessingException e) {
            LOGGER.severe("Can happen, but I'm too lazy to handle this right now!");
            return new NoopEvent();
        }
    }
}
