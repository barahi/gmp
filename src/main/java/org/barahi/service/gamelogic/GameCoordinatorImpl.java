package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

public class GameCoordinatorImpl implements GameCoordinator {
  private final GameLogicService gameLogicService;
  private final RoundLogicService roundLogicService;
  private int roundNumber;

  @Inject
  public GameCoordinatorImpl(GameLogicService gameLogicService, RoundLogicService roundLogicService){
    this.gameLogicService = gameLogicService;
    this.roundLogicService = roundLogicService;
  }

  @Override
  public char startNewGame(RoomId roomId){
    gameLogicService.initGame(roomId);
    roundNumber = 1;
    return roundLogicService.startRound(roomId, roundNumber);
  }
  @Override
  public void storeAnswers(RoomId roomId, CategoryId categoryId, int roundNumber, Map<PlayerId, String> playerAnswers){
    roundLogicService.storeAnswers(roomId, categoryId, roundNumber, playerAnswers);
  }
  @Override
  public Map<PlayerId, Integer> calculatePlayerScoreForRound(RoomId roomId, int roundNumber) {
    return roundLogicService.calculatePlayerScoreForRound(roomId, roundNumber);
  }
  @Override
  public void beginVotePhase(RoomId roomId){
    roundLogicService.beginVotePhase(roomId);
  }
  @Override
  public void  invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, CategoryId categoryId, int roundNum) {
    roundLogicService.invalidatePlayerAnswer(roomId, playerId, categoryId, roundNum);
  }
  @Override
  public void endRound(RoomId roomId) {
    roundLogicService.endRound(roomId);
    startNextRound(roomId);
  }
  @Override
  public Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, int currRound) {
    Map<PlayerId, Integer> scores = roundLogicService.calculatePlayerScoreForRound(roomId, currRound);
    gameLogicService.updatePlayerScores(roomId, scores);
    return scores;
  }
  @Override
  public void startNextRound(RoomId roomId) {
    int numberOfRounds = gameLogicService.getNumberOfRounds(roomId);
    if (roundNumber < numberOfRounds){
      roundNumber++;
      roundLogicService.startRound(roomId, roundNumber);
    } else {
      endGame(roomId);
    }
  }
  @Override
  public Map<PlayerId, Integer> endGame(RoomId roomId) {
    return gameLogicService.endGame(roomId);
  }
  }
