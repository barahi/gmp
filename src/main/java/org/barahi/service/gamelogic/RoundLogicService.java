package org.barahi.service.gamelogic;

import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;
import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {
    char startRound(RoomId roomId, int roundNumber);
    void storeAnswers(RoomId roomId, CategoryId categoryId, int roundNumber, Map<PlayerId, String> playerAnswers);
    Map<String, Map<PlayerId, Integer>>  calculatePlayerScoreForRound(RoomId roomId, int roundNumber);
    void beginVotePhase(RoomId roomId);
    void submitVote(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, boolean vote);
    boolean answerGotApproved(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId);
    void invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, CategoryId category, int roundNum);
    Map<PlayerId, Integer> finalizeRoundScores(RoomId roomId, int roundNumber);
    void endRound(RoomId roomId);
}
