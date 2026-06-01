package org.barahi.server.resource.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.barahi.infra.Functional;
import org.barahi.infra.LoggerFactory;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.server.json.BeginVotePayloadEventJson;
import org.barahi.server.json.RoundScoreEventPayloadJson;
import org.barahi.server.json.SubmitAnswerPayloadEventJson;
import org.barahi.server.json.SubmitVoteEventPayloadJson;
import org.barahi.server.resource.GuiceWebSocketConfigurator;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhaseEvent;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhasePayload;
import org.barahi.server.resource.socket.events.noop.NoopEvent;
import org.barahi.server.resource.socket.events.playerjoined.PlayerJoinedEvent;
import org.barahi.server.resource.socket.events.roundscore.RoundScoreEvent;
import org.barahi.server.resource.socket.events.roundscore.RoundScoreEventPayload;
import org.barahi.server.resource.socket.events.startround.StartRoundEvent;
import org.barahi.server.resource.socket.events.startround.StartRoundEventPayload;
import org.barahi.server.resource.socket.events.submitanswers.SubmitAnswersEvent;
import org.barahi.server.resource.socket.events.submitanswers.SubmitAnswersEventPayload;
import org.barahi.server.resource.socket.events.submitvote.SubmitVoteEvent;
import org.barahi.server.resource.socket.events.submitvote.SubmitVoteEventPayload;
import org.barahi.server.serializer.BeginVotePayloadEventSerializer;
import org.barahi.server.serializer.RoundScoreEventPayloadSerializer;
import org.barahi.server.serializer.SubmitAnswerPayloadEventSerializer;
import org.barahi.server.serializer.SubmitVoteEventPayloadSerializer;
import org.barahi.service.gamelogic.GameCoordinator;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerService;

import jakarta.inject.Inject;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/tootiefrootie/{playerId}", configurator = GuiceWebSocketConfigurator.class)
public class SocketResource {
    private static final ConcurrentHashMap<PlayerId, Session> PLAYER_SESSIONS = new ConcurrentHashMap<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.createLogger(SocketResource.class);
    private final PlayerService playerService;
    private final RoomService roomService;
    private final GameCoordinator gameCoordinator;
    private final SubmitAnswerPayloadEventSerializer submitAnswerPayloadEventSerializer;
    private final RoundScoreEventPayloadSerializer roundScoreEventPayloadSerializer;
    private final SubmitVoteEventPayloadSerializer voteInvalidEventPayloadSerializer;

    private final BeginVotePayloadEventSerializer beginVotePayloadEventSerializer;

    @Inject
    public SocketResource(PlayerService playerService, RoomService roomService, GameCoordinator gameCoordinator, SubmitAnswerPayloadEventSerializer submitAnswerPayloadEventSerializer, RoundScoreEventPayloadSerializer roundScoreEventPayloadSerializer, SubmitVoteEventPayloadSerializer voteInvalidEventPayloadSerializer, BeginVotePayloadEventSerializer beginVotePayloadEventSerializer) {
        this.playerService = playerService;
        this.roomService = roomService;
        this.gameCoordinator = gameCoordinator;
        this.submitAnswerPayloadEventSerializer = submitAnswerPayloadEventSerializer;
        this.roundScoreEventPayloadSerializer = roundScoreEventPayloadSerializer;
        this.voteInvalidEventPayloadSerializer = voteInvalidEventPayloadSerializer;
        this.beginVotePayloadEventSerializer = beginVotePayloadEventSerializer;
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

        PLAYER_SESSIONS.put(player.getId(), session);
        RoomId roomId = null;
        try {
            roomId = roomService.getRoomIdForPlayer(playerId);
        } catch (ObjectNotFoundException e) {
            broadcastErrorAndCloseSession(session, "Player not in any room: " + playerId);
        }
        List<Player> players = roomService.getPlayersInRoom(roomId);
        broadcastEventToRoom(roomId, PlayerJoinedEvent.withListOfPlayers(players));
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("playerId") String rawPlayerId) {
        LOGGER.info("Received message from " + session.getId() + ": " + message);

        PlayerId playerId = PlayerId.of(rawPlayerId);
        RoomId roomId;
        try {
            roomId = roomService.getRoomIdForPlayer(playerId);
        } catch (ObjectNotFoundException e) {
            broadcastError(session, "Player not in any room:" + playerId);
            return;
        }

        Event<?> event = getEventFromString(message);
        EventType eventType = EventType.valueOf(event.getType());
        switch (eventType) {
            case START_ROUND: {
                char letterForRound = gameCoordinator.startNewGame(roomId);
                StartRoundEventPayload payload = new StartRoundEventPayload(letterForRound, 1);
                StartRoundEvent startRoundEvent = new StartRoundEvent(payload);
                broadcastEventToRoom(roomId, startRoundEvent);
                break;
            }

            case SUBMIT_ANSWERS: {
                SubmitAnswersEvent submitAnswersEvent = (SubmitAnswersEvent) event;
                SubmitAnswerPayloadEventJson jsonIncomingPayload = submitAnswersEvent.getPayload();
                SubmitAnswersEventPayload serializedPayload = submitAnswerPayloadEventSerializer.fromJson(jsonIncomingPayload);

                gameCoordinator.storeAnswers(roomId, serializedPayload.getCategoryId(), serializedPayload.getRoundNumber(), serializedPayload.getPlayerAnswers());
                Map<String, Map<PlayerId, Integer>> roundScores = gameCoordinator.calculatePlayerScoreForRound(roomId, serializedPayload.getRoundNumber());

                RoundScoreEventPayload outgoingPayload = new RoundScoreEventPayload(serializedPayload.getRoundNumber(), roundScores);
                RoundScoreEventPayloadJson outgoingPayloadJson = roundScoreEventPayloadSerializer.toJson(outgoingPayload);

                RoundScoreEvent roundScoreEvent = new RoundScoreEvent(outgoingPayloadJson);
                broadcastEventToRoom(roomId, roundScoreEvent);
                break;
            }
            case BEGIN_VOTE_PHASE: {
                gameCoordinator.beginVotePhase(roomId);
            }

            case SUBMIT_VOTE: {
                SubmitVoteEvent voteInvalidEvent = (SubmitVoteEvent) event;
                SubmitVoteEventPayloadJson jsonIncomingPayload = voteInvalidEvent.getPayload();
                SubmitVoteEventPayload serializedPayload = voteInvalidEventPayloadSerializer.fromJson(jsonIncomingPayload);
                gameCoordinator.submitVote(roomId, serializedPayload.getCategoryId(), serializedPayload.getRoundNumber(), serializedPayload.getTargetPlayerId(), serializedPayload.getVoterId(), serializedPayload.getVote());
                break;
            }

            // TO DO: add event FINALIZE_VOTE_PHASE (re calculate scores), return scores and start new round
            // TO DO: add END_GAME that returns the cumulative scores/


            case NOOP: {
                // Do Nothing.
                break;
            }
            case PLAYER_LEFT:
            case PLAYER_JOINED:
            default: {
                throw new IllegalStateException("Unexpected value: " + event.getType());
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("playerId") String rawPlayerId) {
        PlayerId playerId = PlayerId.of(rawPlayerId);
        PLAYER_SESSIONS.remove(playerId);
        LOGGER.info("WebSocket connection closed: " + session.getId());
    }

    public void broadcastEventToRoom(RoomId roomId, Event<?> event) {
        List<Player> players = roomService.getPlayersInRoom(roomId);
        broadcastEventToRoom(players, event);
    }

    public void broadcastEventToRoom(List<Player> players, Event<?> event) {
        String eventDetails = writeEventAsString(event);
        List<Session> sessions = Functional.map(players, player -> PLAYER_SESSIONS.get(player.getId()));
        List<Session> validSessions = Functional.filter(sessions, session -> session != null && session.isOpen());
        validSessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText(eventDetails);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
            LOGGER.severe("Failed to serialize event for broadcasting: " + e.getMessage());
            throw new RuntimeException("Failed to serialize event for broadcasting", e);
        }
    }

    private Event<?> getEventFromString(String eventDetails) {
        try {
            return OBJECT_MAPPER.readValue(eventDetails, Event.class);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Jackson parsing failed!", e);
            return new NoopEvent();
        }
    }
}
