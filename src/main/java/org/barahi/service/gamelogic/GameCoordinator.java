package org.barahi.service.gamelogic;

import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

public interface GameCoordinator {
 char startNewGame(RoomId roomId);
 void storeAnswers(RoomId roomId, CategoryId categoryId, int roundNumber, Map<PlayerId, String> playerAnswers);
 Map<String, Map<PlayerId, Integer>>  calculatePlayerScoreForRound(RoomId roomId, int roundNumber);
 void beginVotePhase(RoomId roomId);
 void submitVote(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, boolean vote);
 boolean finalizeVotePhase(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId);
 void endRound(RoomId roomId);
 Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, int currRound);
 void startNextRound(RoomId roomId);
 Map<PlayerId, Integer> endGame(RoomId roomId);
}
