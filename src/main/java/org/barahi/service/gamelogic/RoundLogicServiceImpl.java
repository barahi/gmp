package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.serviceapi.room.RoundPhase;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoundLogicServiceImpl implements RoundLogicService {
  private static final int CATEGORY_FULL_SCORE = 100;

  private final RoomService roomService;
  private final PlayerAnswerStore playerAnswerStore;
  private final GameStateStore gameStateStore;
  private final GameLogicService gameLogicService;
  private final GameSettingsStore gameSettingsStore;


  @Inject
  public RoundLogicServiceImpl(RoomService roomService, PlayerAnswerStore playerAnswerStore, GameStateStore gameStateStore, GameLogicService gameLogicService, GameSettingsStore gameSettingsStore){
    this.roomService = roomService;
    this.playerAnswerStore = playerAnswerStore;
    this.gameStateStore = gameStateStore;
    this.gameSettingsStore = gameSettingsStore;
    this.gameLogicService = gameLogicService;
  }

  @Override
  public char startRound(RoomId roomId, int roundNumber) {
    gameStateStore.changeGamePhaseAndRound(roomId, RoundPhase.SUBMIT, roundNumber);
    String excluded = gameSettingsStore.getLetterExclusions(roomId).stream().map(String::valueOf).collect(Collectors.joining());
    String allChars = "abcdefghijklmnopqrstuvwxyz";
    String allowedChars = allChars.replace("[" + excluded + "]", "");
    int randomIdx = (int)(Math.random() * allowedChars.length());
    return allowedChars.charAt(randomIdx);
  }

  @Override
  public void storeAnswers(RoomId roomId, Map<PlayerId, String> playerAnswers, String category, int roundNumber) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SUBMIT);
    playerAnswerStore.storeAnswers(playerAnswers, category, roundNumber);
  }

  @Override
  public Map<PlayerId, Integer> calculatePlayerScoreForRound(RoomId roomId, int roundNumber) {
//    List<PlayerId> playerIds = roomService.getPlayerIdsForRoom(roomId);
    List<PlayerId> playerIds = List.of();
    Map<String, Map<PlayerId, String>> categoryToPlayerAnswers = playerAnswerStore.getAnswersForRound(playerIds, roundNumber);

    // map to calculate answer occurrence per category
    Map<String, Integer> answerCount = new HashMap<>();
    categoryToPlayerAnswers.forEach((category, playerIdToAnswers) -> {
      playerIdToAnswers.forEach((playerId, answer) -> {
        if (answer != null && !answer.isEmpty()) {
          answerCount.put(answer, answerCount.getOrDefault(answer, 0) + 1);
        }
      });
    });

    // what will be returned
    Map<PlayerId, Integer> roundScores = new HashMap<>();
    categoryToPlayerAnswers.forEach((category, playerIdToAnswers) -> {
      playerIdToAnswers.forEach((playerId, answer) -> {
        if (answerCount.containsKey(answer)){
          int score = CATEGORY_FULL_SCORE / (answerCount.get(answer));
          roundScores.put(playerId, roundScores.
            get(playerId) + score);
        }
      });
    });
    return roundScores;
  }

  @Override
  public void beginVotePhase(RoomId roomId){
    gameStateStore.changeGamePhase(roomId, RoundPhase.VOTE);
  }
  @Override
  public void invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, String category) {
    playerAnswerStore.updateScoreForAnswer(playerId, category, 0);
  }

  @Override
  public void endRound(RoomId roomId) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SCORE);
    gameLogicService.startNextRound(roomId);
  }
}

