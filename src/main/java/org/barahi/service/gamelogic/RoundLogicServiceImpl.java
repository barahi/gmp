package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhasePayload;
import org.barahi.service.gamelogic.Dto.FlaggedAnswer;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.gameSettings.GameSettings.GameSettingsId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerService;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;
import org.barahi.store.gamelogic.PlayerVoteStore;
import org.jooq.meta.derby.sys.Sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.barahi.service.gamelogic.RoundLogicHelper.generateRandomCharExcluding;

public class RoundLogicServiceImpl implements RoundLogicService {
  private static final int CATEGORY_FULL_SCORE = 100;
  private final PlayerService playerService;
  private final RoomService roomService;
  private final PlayerAnswerStore playerAnswerStore;
  private final GameStateStore gameStateStore;
  private final GameSettingsStore gameSettingsStore;

  private final PlayerVoteStore playerVoteStore;

  @Inject
  public RoundLogicServiceImpl(PlayerService playerService, RoomService roomService, PlayerAnswerStore playerAnswerStore, GameStateStore gameStateStore, GameSettingsStore gameSettingsStore, PlayerVoteStore playerVoteStore){
    this.playerService = playerService;
    this.roomService = roomService;
    this.playerAnswerStore = playerAnswerStore;
    this.gameStateStore = gameStateStore;
    this.gameSettingsStore = gameSettingsStore;
    this.playerVoteStore = playerVoteStore;
  }

  @Override
  public Integer getCurrentRoundNumber(RoomId roomId){
    return gameStateStore.getCurrentRound(roomId) != null? gameStateStore.getCurrentRound(roomId) : 1;
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
  public void storeAnswers(RoomId roomId, int round, PlayerId playerId, Map<String, String> roundAnswers) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SUBMIT);
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    Map<CategoryId, String> categoryIdStringMap = new HashMap<>();
    roundAnswers.forEach((categoryStr, answer) -> {
      CategoryId categoryId = gameSettingsStore.getCategoryIdFromName(categoryStr, gameSettingsId);
      String cleanedAnswer = answer.toLowerCase().trim();
      categoryIdStringMap.put(categoryId, cleanedAnswer);
    });
    playerAnswerStore.storeAnswers(gameSettingsId, round, playerId, categoryIdStringMap);
  }

  @Override
  public Integer getNumberOfSubmittedAnswers(int roundNumber, RoomId roomId){
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    return playerAnswerStore.getPlayerAnswersForRound(roundNumber, gameSettingsId);
  }

  @Override
  public Map<String, Map<String, PlayerAnswer>> calculatePlayerScoreForRound(RoomId roomId, int roundNumber) {
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    List<PlayerId> playerIds = roomService.getPlayerIdsInRoom(roomId);
    char currentLetter = gameStateStore.getLetterForCurrentRound(roomId, roundNumber);
    Map<String, Map<PlayerId, String>> categoryToPlayerAnswers = playerAnswerStore.getAnswersForRound(playerIds, roundNumber);
    Map<String, Map<String, PlayerAnswer>> roundScores = new HashMap<>();

    categoryToPlayerAnswers.forEach((category, playerAnswer) -> {
      Map<String, Integer> answerCount = new HashMap<>();
      playerAnswer.forEach((playerId, answer) -> {
        if (answer != null && !answer.isEmpty()){
          String cleanedAnswer = answer.toLowerCase();
          if (cleanedAnswer.startsWith(String.valueOf(currentLetter))){
            answerCount.put(cleanedAnswer, answerCount.getOrDefault(cleanedAnswer,0) + 1);
          }
        }
      });

      Map<String, PlayerAnswer> playerScores = new HashMap<>();
      playerAnswer.forEach((playerId, answer) -> {
        int calculatedScore = 0;
        String rawAnswer = (answer == null) ? "" : answer.trim();
        String cleanedAnswer = rawAnswer.toLowerCase();
        if (cleanedAnswer.startsWith(String.valueOf(currentLetter)) && answerCount.containsKey(cleanedAnswer)){
          calculatedScore = CATEGORY_FULL_SCORE / answerCount.get(cleanedAnswer);
        }

        String username = playerService.getUsernameFromId(playerId);
        PlayerAnswer playerAnswerScore = new PlayerAnswer(answer, calculatedScore);
        playerScores.put(username, playerAnswerScore);
        CategoryId categoryId = gameSettingsStore.getCategoryIdFromName(category, gameSettingsId);
        playerAnswerStore.updateScoreForAnswer(playerId, categoryId, roundNumber, calculatedScore);
      });
      roundScores.put(category, playerScores);
    });
    return roundScores;
  }


  @Override
  public FlaggedAnswer beginVotePhase(RoomId roomId, String targetedPlayer, String triggeredByPlayer, String category, int roundNumber, String answer){
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    CategoryId categoryId = gameSettingsStore.getCategoryIdFromName(category, gameSettingsId);
    PlayerId targetedPlayerId = playerService.getIdFromUsername(targetedPlayer);
    String playerAnswerId = playerAnswerStore.findPlayerAnswerId(gameSettingsId, roundNumber, categoryId, targetedPlayerId, answer);
    playerVoteStore.flagPlayerAnswer(roomId, playerAnswerId, targetedPlayerId);
    gameStateStore.changeGamePhase(roomId, RoundPhase.VOTE);
    return new FlaggedAnswer(
      category,
      targetedPlayer,
      triggeredByPlayer,
      answer,
      playerAnswerStore.getPlayerAnswerScore(playerAnswerId)
    );
  }

  @Override
  public void submitVote(RoomId roomId, String category, int roundNumber, String targetPlayer, String voterPlayer, boolean vote){
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    CategoryId categoryId = gameSettingsStore.getCategoryIdFromName(category, gameSettingsId);
    playerVoteStore.savePlayerVote(roomId, categoryId, roundNumber, playerService.getIdFromUsername(targetPlayer), playerService.getIdFromUsername(voterPlayer), vote);
  }

  @Override
  public VoteRoundResults getVoteRoundResults(RoomId roomId, String category, int roundNumber, String targetPlayer){
    CategoryId categoryId = gameSettingsStore.getCategoryIdFromName(category, gameSettingsStore.getGameSettingsId(roomId));
    return playerVoteStore.getVoteRoundResults(roomId, categoryId, category, roundNumber, playerService.getIdFromUsername(targetPlayer), targetPlayer);
  }

  @Override
    public void invalidatePlayerAnswer(RoomId roomId, String category, String answer, int roundNum) {
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    CategoryId categoryId = gameSettingsStore.getCategoryIdFromName(category, gameSettingsId);
    playerAnswerStore.invalidateRoundAnswer(gameSettingsId, categoryId, answer, roundNum, 0);
  }

  @Override
  public Map<PlayerId, Integer> finalizeRoundScores(RoomId roomId, int roundNumber){
    GameSettingsId gameSettingsId = gameSettingsStore.getGameSettingsId(roomId);
    return playerAnswerStore.getScoresForRound(gameSettingsId, roundNumber);
  }

  @Override
  public void endRound(RoomId roomId) {
    gameStateStore.changeGamePhase(roomId, RoundPhase.SCORE);
  }

}

