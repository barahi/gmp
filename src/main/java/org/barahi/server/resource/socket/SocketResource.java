package org.barahi.server.resource.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.barahi.infra.Functional;
import org.barahi.infra.LoggerFactory;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.infra.exceptions.RoomAuthenticationException;
import org.barahi.infra.exceptions.RoomFullException;
import org.barahi.server.json.*;
import org.barahi.server.resource.GuiceWebSocketConfigurator;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhaseEvent;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhasePayload;
import org.barahi.server.resource.socket.events.endgame.EndGameEvent;
import org.barahi.server.resource.socket.events.endround.EndRoundEvent;
import org.barahi.server.resource.socket.events.endround.RoundResultsEvent;
import org.barahi.server.resource.socket.events.flaggedanswer.FlaggedAnswerEvent;
import org.barahi.server.resource.socket.events.noop.NoopEvent;
import org.barahi.server.resource.socket.events.playerjoined.PlayerJoinedEvent;
import org.barahi.server.resource.socket.events.playerjoined.PlayerJoinedEventPayload;
import org.barahi.server.resource.socket.events.roundscore.RoundScoreEvent;
import org.barahi.server.resource.socket.events.roundscore.RoundScoreEventPayload;
import org.barahi.server.resource.socket.events.startround.StartRoundEvent;
import org.barahi.server.resource.socket.events.startround.StartRoundEventPayload;
import org.barahi.server.resource.socket.events.submitanswers.SubmitAnswersEvent;
import org.barahi.server.resource.socket.events.submitanswers.SubmitAnswersEventPayload;
import org.barahi.server.resource.socket.events.submitvote.SubmitVoteEvent;
import org.barahi.server.resource.socket.events.submitvote.SubmitVoteEventPayload;
import org.barahi.server.resource.socket.events.timeupevent.TimeUpEvent;
import org.barahi.server.resource.socket.events.voteresult.EndVoteRoundEvent;
import org.barahi.server.resource.socket.events.voteresult.VoteResultsEvent;
import org.barahi.server.serializer.*;
import org.barahi.service.gamelogic.Dto.FlaggedAnswer;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.service.gamelogic.GameCoordinator;
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
import java.util.ArrayList;
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
    private final BeginVotePayloadEventSerializer beginVotePayloadEventSerializer;
    private final SubmitVoteEventPayloadSerializer submitVoteEventPayloadSerializer;
    private final VoteResultsEventPayloadSerializer voteResultsEventPayloadSerializer;
    private final PlayerScoresPayloadEventSerializer playerScoresPayloadEventSerializer;
    private final EndGamePayloadEventSerializer endGamePayloadEventSerializer;


    @Inject
    public SocketResource(PlayerService playerService, RoomService roomService, GameCoordinator gameCoordinator, SubmitAnswerPayloadEventSerializer submitAnswerPayloadEventSerializer,
                          RoundScoreEventPayloadSerializer roundScoreEventPayloadSerializer, BeginVotePayloadEventSerializer beginVotePayloadEventSerializer, SubmitVoteEventPayloadSerializer submitVoteEventPayloadSerializer, VoteResultsEventPayloadSerializer
                                voteResultsEventPayloadSerializer, PlayerScoresPayloadEventSerializer playerScoresPayloadEventSerializer, EndGamePayloadEventSerializer endGamePayloadEventSerializer) {
        this.playerService = playerService;
        this.roomService = roomService;
        this.gameCoordinator = gameCoordinator;
        this.submitAnswerPayloadEventSerializer = submitAnswerPayloadEventSerializer;
        this.roundScoreEventPayloadSerializer = roundScoreEventPayloadSerializer;
        this.beginVotePayloadEventSerializer = beginVotePayloadEventSerializer;
        this.submitVoteEventPayloadSerializer = submitVoteEventPayloadSerializer;
        this.voteResultsEventPayloadSerializer = voteResultsEventPayloadSerializer;
        this.playerScoresPayloadEventSerializer = playerScoresPayloadEventSerializer;
        this.endGamePayloadEventSerializer = endGamePayloadEventSerializer;
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

        Map<String, List<String>> queryParams = session.getRequestParameterMap();
        String rawRoomId = Iterables.getOnlyElement(queryParams.get("roomId"));
        List<String> rpl = queryParams.get("roomPassword");
        String roomPassword = rpl == null ? null : Iterables.getOnlyElement(rpl);
        RoomId roomId = RoomId.of(rawRoomId);

        try {
            if (!Functional.contains(roomService.getPlayerIdsInRoom(roomId), playerId)) {
                roomService.addPlayerToRoom(rawRoomId, new JoinRoomJson().setPlayerId(rawPlayerId).setPassword(roomPassword));
            }

            PLAYER_SESSIONS.put(playerId, session);
            PlayerJoinedEventPayload payload = new PlayerJoinedEventPayload();
            payload.setPlayers(roomService.getPlayersInRoom(roomId));
            payload.setSettings(roomService.getRoomSettings(roomId));
            PlayerJoinedEvent playerJoinedEvent = new PlayerJoinedEvent();
            playerJoinedEvent.setPayload(payload);
            broadcastEventToRoom(roomId, playerJoinedEvent);

        } catch (RoomAuthenticationException e) {
            broadcastError(session, e.getMessage());
            closeSessionGracefully(session, 4001, e.getMessage());

        } catch (RoomFullException e) {
            broadcastError(session,"This game lobby is full");
            closeSessionGracefully(session, 4002, "This game lobby is full.");

        } catch (Exception e) {
            broadcastError(session,"Internal server error.");
            System.out.println("internal server error: " + e.getMessage());
            closeSessionGracefully(session, 1011, "Internal server error.");
        }

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
                int currRound = gameCoordinator.getCurrentRoundNumber(roomId);
                if (currRound < 1){
                    gameCoordinator.startNewGame(roomId);
                }
                List<Player> playersToAlert = getConnectedPlayersInRoom(roomId);
                char letterForRound = gameCoordinator.startRound(roomId, currRound, () -> {
                    broadcastEventToRoom(playersToAlert, new TimeUpEvent());
                });
                StartRoundEventPayload payload = new StartRoundEventPayload(letterForRound, currRound);
                StartRoundEvent startRoundEvent = new StartRoundEvent(payload);
                broadcastEventToRoom(roomId, startRoundEvent);
                break;
            }

            case SUBMIT_ANSWERS: {
                SubmitAnswersEvent submitAnswersEvent = (SubmitAnswersEvent) event;
                SubmitAnswerPayloadJson jsonIncomingPayload = submitAnswersEvent.getPayload();
                SubmitAnswersEventPayload serializedPayload = submitAnswerPayloadEventSerializer.fromJson(jsonIncomingPayload);
                gameCoordinator.storeAnswers(roomId, serializedPayload.getRoundNumber(), serializedPayload.getPlayerId(), serializedPayload.getRoundAnswers());

                int currentRound = gameCoordinator.getCurrentRoundNumber(roomId);
                int totalConnectedPlayers = getConnectedPlayersInRoom(roomId).size();
                int numberOfSubmittedAnswers = gameCoordinator.getNumberOfSubmittedAnswersInRound(currentRound, roomId);

                if (numberOfSubmittedAnswers >= totalConnectedPlayers){
                    Map<String, Map<String, PlayerAnswer>> roundScores = gameCoordinator.calculatePlayerScoreForRound(roomId, currentRound);
                    RoundScoreEventPayload outgoingPayload = new RoundScoreEventPayload(currentRound, roundScores);
                    RoundScorePayloadJson outgoingPayloadJson = roundScoreEventPayloadSerializer.toJson(outgoingPayload);
                    RoundScoreEvent roundScoreEvent = new RoundScoreEvent(outgoingPayloadJson);
                    broadcastEventToRoom(roomId, roundScoreEvent);
                }
                break;
            }

            case BEGIN_VOTE_PHASE: {
                int currentRound = gameCoordinator.getCurrentRoundNumber(roomId);
                BeginVotePhaseEvent beginVotePhaseEvent = (BeginVotePhaseEvent) event;
                BeginVotePayloadJson incomingPayload = beginVotePhaseEvent.getPayload();
                BeginVotePhasePayload serializedPayload = beginVotePayloadEventSerializer.fromJson(incomingPayload);

                FlaggedAnswer flaggedAnswer = gameCoordinator.beginVotePhase(roomId, serializedPayload.getTargetedPlayer(), serializedPayload.getTriggeredByPlayer(), serializedPayload.getCategory(), currentRound, serializedPayload.getAnswer());
                FlaggedAnswerPayloadJson outgoingJson = beginVotePayloadEventSerializer.toJson(flaggedAnswer);
                FlaggedAnswerEvent flaggedAnswerEvent = new FlaggedAnswerEvent(outgoingJson);
                broadcastEventToRoom(roomId, flaggedAnswerEvent);
                break;
            }

            case SUBMIT_VOTE: {
                SubmitVoteEvent submitVoteEvent = (SubmitVoteEvent) event;
                SubmitVotePayloadJson jsonIncomingPayload = submitVoteEvent.getPayload();
                SubmitVoteEventPayload serializedPayload = submitVoteEventPayloadSerializer.fromJson(jsonIncomingPayload);
                int currentRound = gameCoordinator.getCurrentRoundNumber(roomId);
                gameCoordinator.submitVote(roomId, serializedPayload.getCategory(), currentRound, serializedPayload.getTargetPlayer(), serializedPayload.getVoterPlayer(),
                  serializedPayload.getVote());
                break;
            }

            case END_VOTE_ROUND: {
                EndVoteRoundEvent endVotePhaseEvent = (EndVoteRoundEvent)event;
                EndVoteRoundPayloadJson incomingJson = endVotePhaseEvent.getPayload();
                int currRound = gameCoordinator.getCurrentRoundNumber(roomId);
                handleEndVoteRound(
                  roomId,
                  incomingJson.getCategory(),
                  currRound,
                  incomingJson.getTargetPlayer()
                );
                break;
            }

            case END_ROUND: {
                EndRoundEvent endRoundEvent = (EndRoundEvent) event;
                int currRound = gameCoordinator.getCurrentRoundNumber(roomId);

                Map<String, Integer> playerScores = gameCoordinator.updatePlayerScores(roomId, currRound);

                RoundResultsPayloadJson serializedJson = playerScoresPayloadEventSerializer.toJson(currRound, playerScores);
                RoundResultsEvent outgoingEvent = new RoundResultsEvent(serializedJson);

                broadcastEventToRoom(roomId, outgoingEvent);
                gameCoordinator.endRound(roomId, currRound);
                break;
            }

            case END_GAME: {
                Map<String, Integer> scores = gameCoordinator.endGame(roomId);
                EndGamePayloadJson serializedJson = endGamePayloadEventSerializer.toJson(scores);
                EndGameEvent outgoingEvent = new EndGameEvent(serializedJson);
                broadcastEventToRoom(roomId, outgoingEvent);
                break;
            }

            case NOOP: {
                break;
            }

            case PLAYER_LEFT:
                break;

            default: {
                throw new IllegalStateException("Unexpected value: " + event.getType());
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("playerId") String rawPlayerId) {
        PlayerId playerId = PlayerId.of(rawPlayerId);
        PLAYER_SESSIONS.remove(playerId);
        // TODO: Remove player from room?
        LOGGER.info("WebSocket connection closed: " + session.getId() + " for player " + playerId.getId().toString());
    }

    private List<Player> getConnectedPlayersInRoom(RoomId roomId){
        List<Player> dbPlayers = roomService.getPlayersInRoom(roomId);
        List<Player> activePlayers = new ArrayList<>();
        dbPlayers.forEach(p -> {
            System.out.println("player: " + p.getUsername());
            Session s = PLAYER_SESSIONS.get(p.getId());
            if (s!=null && s.isOpen()) {
                activePlayers.add(p);
            }
        });
        return activePlayers;
    }

    private void closeSessionGracefully(Session session, int statusCode, String reasonMessage) {
        try {
            if (session.isOpen()) {
                CloseReason reason = new CloseReason(
                  CloseReason.CloseCodes.getCloseCode(statusCode),
                  reasonMessage.length() > 123 ? reasonMessage.substring(0, 120) + "..." : reasonMessage
                );
                session.close(reason);
            }
        } catch (IOException e) {
            System.err.println("Failed to cleanly disconnect session: " + e.getMessage());
        }
    }


    public void broadcastEventToRoom(RoomId roomId, Event<?> event) {
        List<Player> players = roomService.getPlayersInRoom(roomId);
        broadcastEventToRoom(players, event);
    }

    private void handleEndVoteRound(RoomId roomId, String category, int roundNumber, String targetPlayer) {
        VoteRoundResults voteResults = gameCoordinator.getVoteResults(roomId, category, roundNumber, targetPlayer);
        VoteResultsPayloadJson serializedJson = voteResultsEventPayloadSerializer.toJson(voteResults);
        VoteResultsEvent outgoingEvent = new VoteResultsEvent(serializedJson);
        System.out.println("broadcasting vote results");
        broadcastEventToRoom(roomId, outgoingEvent);
        gameCoordinator.finalizeVotePhase(roomId, category, roundNumber, targetPlayer);
    }

    public void broadcastEventToRoom(List<Player> players, Event<?> event) {
        String eventDetails = writeEventAsString(event);
        List<Session> sessions = Functional.map(players, player -> PLAYER_SESSIONS.get(player.getId()));

        List<Session> validSessions = Functional.filter(sessions, session -> session != null && session.isOpen());
        validSessions.forEach(session -> {
            session.getAsyncRemote().sendText(eventDetails, result -> {
                if (!result.isOK()){
                    LOGGER.severe("Failed to send asynchronous message to session " + session.getId());
                }
            });
        });
    }

    private void broadcastError(Session session, String message) {
        LOGGER.severe(message);
        String jsonError = String.format("{\"type\":\"ERROR\", \"payload\":{\"message\":\"%s\"}}", message);
        if (session.isOpen()){
            session.getAsyncRemote().sendText(jsonError);
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

    @OnError
    private void onError(Session session, Throwable throwable, @PathParam("playerId") String rawPlayerId){
        LOGGER.severe("websocket error for player " + rawPlayerId + " on session " + session.getId());
        throwable.printStackTrace();
    }
}
