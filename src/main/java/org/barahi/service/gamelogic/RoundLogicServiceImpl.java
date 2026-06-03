package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.gameSettings.GameSettings.GameSettingsId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;
import org.barahi.store.gamelogic.PlayerVoteStore;

import java.util.ArrayList;
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

  private final PlayerVoteStore playerVoteStore;

  @Inject
  public RoundLogicServiceImpl(RoomService roomService, PlayerAnswerStore playerAnswerStore, GameStateStore gameStateStore, GameSettingsStore gameSettingsStore, PlayerVoteStore playerVoteStore){
    this.roomService = roomService;
    this.playerAnswerStore = playerAnswerStore;
    this.gameStateStore = gameStateStore;
    this.gameSettingsStore = gameSettingsStore;
    this.playerVoteStore = playerVoteStore;
  }

  @Override
  public int getCurrentRoundNumber(RoomId roomId){
    return gameStateStore.getCurrentRound(roomId);
  }

  @Override
  public char startRound(RoomId roomId, int roundNumber) {
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    List<Character> excludedLetters = gameSettingsStore.getLetterExclusions(gameSettingsId);
    char letterGenerated = generateRandomCharExcluding(excludedLetters);
    gameStateStore.changeGamePhaseAndRound(roomId, RoundPhase.SUBMIT, roundNumber, letterGenerated);
    gameSettingsStore.addNewLetterToExcludedLetters(gameSettingsId, letterGenerated);
    return letterGenerated;
  }

  @Override
  public void storeAnswers(RoomId roomId, int round, PlayerId playerId, Map<CategoryId, String> roundAnswers) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SUBMIT);
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    playerAnswerStore.storeAnswers(gameSettingsId, round, playerId, roundAnswers);
  }

  @Override
  public Map<String, Map<PlayerId, Integer>> calculatePlayerScoreForRound(RoomId roomId, int roundNumber) {
    List<PlayerId> playerIds = roomService.getPlayerIdsInRoom(roomId);
    char currentLetter = gameStateStore.getLetterForCurrentRound(roomId, roundNumber);
    Map<String, Map<PlayerId, String>> categoryIdToPlayerAnswers = playerAnswerStore.getAnswersForRound(playerIds, roundNumber);
    Map<String, Map<PlayerId, Integer>> roundScores = new HashMap<>();

    categoryIdToPlayerAnswers.forEach((category, playerAnswer) -> {
      Map<String, Integer> answerCount = new HashMap<>();
      playerAnswer.forEach((playerId, answer) -> {
        if (answer != null && !answer.isEmpty()){
          String cleanedAnswer = answer.toLowerCase();
          answerCount.put(cleanedAnswer, answerCount.getOrDefault(cleanedAnswer,0) + 1);
        }
      });
      Map<PlayerId, Integer> playerScores = new HashMap<>();
      playerAnswer.forEach((playerId, answer) -> {
        if (!answer.isEmpty() && answer.startsWith(String.valueOf(currentLetter))){
          String cleanedAnswer = answer.toLowerCase();
          if (answerCount.containsKey(cleanedAnswer)){
            playerScores.put(playerId, CATEGORY_FULL_SCORE/answerCount.get(cleanedAnswer));
            return;
          }
        }
        playerScores.put(playerId, 0);
      });
      roundScores.put(category, playerScores);
    });
    return roundScores;
  }

  @Override
  public void beginVotePhase(RoomId roomId){
    gameStateStore.changeGamePhase(roomId, RoundPhase.VOTE);
  }

  @Override
  public void submitVote(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, boolean vote){
    playerVoteStore.savePlayerVote(roomId, categoryId, roundNumber, targetPlayerId, voterId, vote);
  }

  @Override
  public VoteRoundResults getVoteRoundResults(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId){
    return playerVoteStore.getVoteRoundResults(roomId, categoryId, roundNumber, targetPlayerId);
  }

  @Override
  public void invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, CategoryId categoryId, int roundNum) {
    playerAnswerStore.updateScoreForAnswer(playerId, categoryId, roundNum, -1);
  }

  @Override
  public Map<PlayerId, Integer> finalizeRoundScores(RoomId roomId, int roundNumber){
    Map<String, Map<PlayerId, Integer>> scores = calculatePlayerScoreForRound(roomId, roundNumber);
    Map<PlayerId, Integer> finalRoundScores = new HashMap<>();
    scores.forEach((category, playerScore) -> {
      playerScore.forEach((playerId, score) -> {
        finalRoundScores.put(playerId, finalRoundScores.getOrDefault(playerId, 0) + score);
      });
    });
    return finalRoundScores;
  }

  @Override
  public void endRound(RoomId roomId) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SCORE);
  }

}

