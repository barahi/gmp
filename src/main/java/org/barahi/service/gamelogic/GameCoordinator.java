package org.barahi.service.gamelogic;

import org.barahi.service.gamelogic.Dto.FlaggedAnswer;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;
import java.util.Map;

public interface GameCoordinator {
 void startNewGame(RoomId roomId);
 char startRound(RoomId roomId, int roundNumber, Runnable onRoundTimeout);
 int getCurrentRoundNumber(RoomId roomId);
 void storeAnswers(RoomId roomId, int round, PlayerId playerId, Map<String, String> roundAnswers);
 Integer getNumberOfSubmittedAnswersInRound(int roundNumber, RoomId roomId);
 Map<String, Map<String, PlayerAnswer>>  calculatePlayerScoreForRound(RoomId roomId, int roundNumber);
 FlaggedAnswer beginVotePhase(RoomId roomId, String targetedPlayer, String triggeredByPlayer, String category, int roundNumber, String answer);
 void startVotingRoundTimer(RoomId roomId, int time, Runnable onTimeout);
 void cancelVotingRoundTimer(RoomId roomId);
 void submitVote(RoomId roomId, String category, int roundNumber, String targetPlayer, String voterPlayer, boolean vote);
 VoteRoundResults getVoteResults(RoomId roomId, String category, int roundNumber, String targetPlayer);
 void finalizeVotePhase(RoomId roomId, String category, String answer, int roundNumber, String targetPlayer);
 Map<String, Integer> updatePlayerScores(RoomId roomId, int currRound);
 void endRound(RoomId roomId, int roundNumber);
 void startNextRound(RoomId roomId, int roundNumber);
 List<String> endGame(RoomId roomId);
}
