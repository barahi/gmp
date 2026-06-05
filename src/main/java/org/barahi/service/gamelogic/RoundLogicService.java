package org.barahi.service.gamelogic;

import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;
import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {
    char startRound(RoomId roomId, int roundNumber);
    Integer getCurrentRoundNumber(RoomId roomId);
    void storeAnswers(RoomId roomId, int round, PlayerId playerId, Map<String, String> roundAnswers);
    Map<String, Map<PlayerId, Integer>>  calculatePlayerScoreForRound(RoomId roomId, int roundNumber);
    void beginVotePhase(RoomId roomId);
    void submitVote(RoomId roomId,  String category, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, boolean vote);
    VoteRoundResults getVoteRoundResults(RoomId roomId, String category, int roundNumber, PlayerId targetPlayerId);
    void invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, String category, int roundNum);
    Map<PlayerId, Integer> finalizeRoundScores(RoomId roomId, int roundNumber);
    void endRound(RoomId roomId);
}
