package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

public class GameCoordinatorImpl implements GameCoordinator {
  private final GameLogicService gameLogicService;
  private final RoundLogicService roundLogicService;

  @Inject
  public GameCoordinatorImpl(GameLogicService gameLogicService, RoundLogicService roundLogicService){
    this.gameLogicService = gameLogicService;
    this.roundLogicService = roundLogicService;
  }

  @Override
  public void startNewGame(RoomId roomId){
    gameLogicService.initGame(roomId);
  }

  @Override
  public char startRound(RoomId roomId, int roundNumber){
    return roundLogicService.startRound(roomId, roundNumber);
  }

  @Override
  public int getCurrentRoundNumber(RoomId roomId){
    return roundLogicService.getCurrentRoundNumber(roomId);
  }
  @Override
  public void storeAnswers(RoomId roomId, int round, PlayerId playerId, Map<String, String> roundAnswers){
    roundLogicService.storeAnswers(roomId, round, playerId, roundAnswers);
  }
  @Override
  public Map<String, Map<PlayerId, Integer>>  calculatePlayerScoreForRound(RoomId roomId, int roundNumber) {
    return roundLogicService.calculatePlayerScoreForRound(roomId, roundNumber);
  }
  @Override
  public void beginVotePhase(RoomId roomId){
    roundLogicService.beginVotePhase(roomId);
  }

  @Override
  public void submitVote(RoomId roomId, String category, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, boolean vote){
    roundLogicService.submitVote(roomId, category, roundNumber, targetPlayerId, voterId, vote);
  }

  @Override
  public VoteRoundResults getVoteResults(RoomId roomId, String category, int roundNumber, PlayerId targetPlayerId){
    return roundLogicService.getVoteRoundResults(roomId, category, roundNumber, targetPlayerId);
  }


  @Override
  public void finalizeVotePhase(RoomId roomId, String category, int roundNumber, PlayerId targetPlayerId){
    VoteRoundResults results  = roundLogicService.getVoteRoundResults(roomId, category, roundNumber, targetPlayerId);
    int totalVotes = results.getValidAnswerVotes() + results.getInvalidAnswerVotes();
    boolean verdict = results.getValidAnswerVotes() >= (totalVotes-1)/2.0;
    if (!verdict){
      roundLogicService.invalidatePlayerAnswer(roomId, targetPlayerId, category, roundNumber);
    }
  }

  @Override
  public Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, int roundNumber) {
    Map<PlayerId, Integer> finalScores = roundLogicService.finalizeRoundScores(roomId, roundNumber);
    return gameLogicService.updatePlayerScores(roomId, finalScores);
  }
  @Override
  public void endRound(RoomId roomId, int roundNumber) {
    roundLogicService.endRound(roomId);
    startNextRound(roomId, roundNumber);
  }

  @Override
  public void startNextRound(RoomId roomId, int roundNumber) {
    int numberOfRounds = gameLogicService.getNumberOfRounds(roomId);
    if (roundNumber < numberOfRounds){
      roundNumber+=1;
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
