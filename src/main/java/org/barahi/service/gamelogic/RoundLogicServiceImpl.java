package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.gameSettings.GameSettings.GameSettingsId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.barahi.service.gamelogic.RoundLogicHelper.generateRandomCharExcluding;

public class RoundLogicServiceImpl implements RoundLogicService {
  private static final int CATEGORY_FULL_SCORE = 100;

  private final RoomService roomService;
  private final PlayerAnswerStore playerAnswerStore;
  private final GameStateStore gameStateStore;
  private final GameSettingsStore gameSettingsStore;


  @Inject
  public RoundLogicServiceImpl(RoomService roomService, PlayerAnswerStore playerAnswerStore, GameStateStore gameStateStore, GameSettingsStore gameSettingsStore){
    this.roomService = roomService;
    this.playerAnswerStore = playerAnswerStore;
    this.gameStateStore = gameStateStore;
    this.gameSettingsStore = gameSettingsStore;
  }

  @Override
  public char startRound(RoomId roomId, int roundNumber) {
    gameStateStore.changeGamePhaseAndRound(roomId, RoundPhase.SUBMIT, roundNumber);
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    List<Character> excludedLetters = gameSettingsStore.getLetterExclusions(gameSettingsId);
    return generateRandomCharExcluding(excludedLetters);
  }

  @Override
  public void storeAnswers(RoomId roomId, CategoryId categoryId, int roundNumber, Map<PlayerId, String> playerAnswers) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SUBMIT);
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    playerAnswerStore.storeAnswers(gameSettingsId, categoryId, roundNumber, playerAnswers);
  }

  @Override
  public Map<PlayerId, Integer> calculatePlayerScoreForRound(RoomId roomId, int roundNumber) {
    List<PlayerId> playerIds = roomService.getPlayerIdsInRoom(roomId);
    Map<String, Map<PlayerId, String>> categoryIdToPlayerAnswers = playerAnswerStore.getAnswersForRound(playerIds, roundNumber);

    Map<PlayerId, Integer> roundScores = new HashMap<>();

    categoryIdToPlayerAnswers.forEach((categoryId, playerAnswer) -> {
        Map<String, Integer> answerCount = new HashMap<>();
        playerAnswer.forEach((playerId, answer) -> {
          if (answer != null && !answer.isEmpty()) {
            answerCount.put(answer, answerCount.getOrDefault(answer, 0) + 1);
          }
        });

        playerAnswer.forEach((playerId, answer) -> {
          if (answer != null && !answer.isEmpty() && answerCount.containsKey(answer)){
            roundScores.put(playerId, roundScores.getOrDefault(playerId, 0) +
              CATEGORY_FULL_SCORE/ answerCount.get(answer));
          }
        });
    }) ;
    return roundScores;
  }

  @Override
  public void beginVotePhase(RoomId roomId){
    gameStateStore.changeGamePhase(roomId, RoundPhase.VOTE);
  }
  @Override
  public void invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, CategoryId categoryId, int roundNum) {
    playerAnswerStore.updateScoreForAnswer(playerId, categoryId, roundNum, 0);
  }

  @Override
  public void endRound(RoomId roomId) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SCORE);
  }

}

