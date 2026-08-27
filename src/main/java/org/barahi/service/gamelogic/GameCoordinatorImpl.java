package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.service.gamelogic.Dto.FlaggedAnswer;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;
import java.util.Map;

public class GameCoordinatorImpl implements GameCoordinator {
  private final GameLogicService gameLogicService;
  private final RoundLogicService roundLogicService;
  private final GameScheduler gameScheduler;

  @Inject
  public GameCoordinatorImpl(GameLogicService gameLogicService, RoundLogicService roundLogicService, GameScheduler gameScheduler){
    this.gameLogicService = gameLogicService;
    this.roundLogicService = roundLogicService;
    this.gameScheduler = gameScheduler;
  }

  @Override
  public void startNewGame(RoomId roomId){
    gameLogicService.initGame(roomId);
  }

  @Override
  public char startRound(RoomId roomId, int roundNumber, Runnable onRoundTimeout){
    char roundLetter = roundLogicService.startRound(roomId, roundNumber);
    int roundDuration = gameLogicService.getRoundDuration(roomId);
    gameScheduler.startRoundTimer(roomId, roundDuration, onRoundTimeout);
    return roundLetter;
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
  public Integer getNumberOfSubmittedAnswersInRound(int roundNumber, RoomId roomId){
    return roundLogicService.getNumberOfSubmittedAnswers(roundNumber, roomId);
  }
  @Override
  public Map<String, Map<String, PlayerAnswer>>  calculatePlayerScoreForRound(RoomId roomId, int roundNumber) {
    return roundLogicService.calculatePlayerScoreForRound(roomId, roundNumber);
  }
  @Override
  public FlaggedAnswer beginVotePhase(RoomId roomId, String targetedPlayer, String triggeredByPlayer, String category, int roundNumber, String answer){
    return roundLogicService.beginVotePhase(roomId, targetedPlayer, triggeredByPlayer, category, roundNumber, answer);
  }

  @Override
  public void startVotingRoundTimer(RoomId roomId, int time, Runnable onTimeout){
    gameScheduler.startRoundTimer(roomId, time, onTimeout);
  }

  @Override
  public void cancelVotingRoundTimer(RoomId roomId) {
    gameScheduler.cancelTimer(roomId);
  }

  @Override
  public void submitVote(RoomId roomId, String category, int roundNumber, String targetPlayer, String voterPlayer, boolean vote){
    roundLogicService.submitVote(roomId, category, roundNumber, targetPlayer, voterPlayer, vote);
  }

  @Override
  public VoteRoundResults getVoteResults(RoomId roomId, String category, int roundNumber, String targetPlayer){
    return roundLogicService.getVoteRoundResults(roomId, category, roundNumber, targetPlayer);
  }


  @Override
  public void finalizeVotePhase(RoomId roomId, String category, String answer, int roundNumber, String targetPlayer){
    VoteRoundResults results  = roundLogicService.getVoteRoundResults(roomId, category, roundNumber, targetPlayer);
    int totalVotes = results.getValidAnswerVotes() + results.getInvalidAnswerVotes();
    int cutOff = Math.ceilDiv(totalVotes, 2);
    boolean verdict = results.getInvalidAnswerVotes() >= cutOff;
    if (verdict){
      roundLogicService.invalidatePlayerAnswer(roomId, category, answer, roundNumber);
    }
  }

  @Override
  public Map<String, Integer> updatePlayerScores(RoomId roomId, int roundNumber) {
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
  public List<String> endGame(RoomId roomId) {
    return gameLogicService.endGame(roomId);
  }
}
