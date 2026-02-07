package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;

import java.util.HashMap;
import java.util.Map;

public class RoundLogicServiceImpl implements RoundLogicService {

  private final PlayerAnswerStore playerAnswerStore;

  private final GameStateStore gameStateStore;
  private final GameLogicService gameLogicService;

  private static final int CATEGORY_FULL_SCORE = 100;

  @Inject
  public RoundLogicServiceImpl(PlayerAnswerStore playerAnswerStore, GameStateStore gameStateStore, GameLogicService gameLogicService){
    this.playerAnswerStore = playerAnswerStore;
    this.gameStateStore = gameStateStore;
    this.gameLogicService = gameLogicService;
  }

  @Override
  public char startRound(String roomId, int roundNumber) {
    gameStateStore.changeGamePhaseAndRound(roomId, "SUBMIT", roundNumber);
    String allChars = "abcdefghijklmnopqrstuvwxyz";
    // TODO: String excluded = excludedLettersStore.getExcludedLetters(roomId) - will delete line 27 when connecting to store
    String excluded = "xyz";
    String allowedChars = allChars.replace("[" + excluded + "]", "");
    int randomIdx = (int)(Math.random() * allowedChars.length());
    // Note: Need to discuss whether we will save characters already picked in excludedLetters table ?
    return allowedChars.charAt(randomIdx);
  }

  @Override
  public void storeAnswers(String roomId, Map<PlayerId, String> playerAnswers, String category, int roundNumber) {
    gameStateStore.changeGamePhase(roomId, "Review");
    playerAnswerStore.storeAnswers(playerAnswers, category, roundNumber);
  }

  @Override
  public Map<PlayerId, Integer> calculatePlayerScoreForRound(String roomId, int roundNumber) {
    Map<String, Map<PlayerId, String>> categoryToPlayerAnswers = playerAnswerStore.getAnswersForRound(roundNumber);

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
  public void invalidatePlayerAnswer(String roomId, PlayerId playerId, String category) {
    gameStateStore.changeGamePhase(roomId, "Vote");
    playerAnswerStore.updateScoreForAnswer(playerId, category, 0);
  }

  @Override
  public void endRound(String roomId) {
    gameStateStore.changeGamePhase(roomId, "Score");
    gameLogicService.startNextRound(roomId);
  }
}

