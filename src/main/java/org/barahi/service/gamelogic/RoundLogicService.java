package org.barahi.service.gamelogic;

import org.barahi.service.gamelogic.Dto.FlaggedAnswer;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {
    char startRound(RoomId roomId, int roundNumber);
    Integer getCurrentRoundNumber(RoomId roomId);
    void storeAnswers(RoomId roomId, int round, PlayerId playerId, Map<String, String> roundAnswers);
    Integer getNumberOfSubmittedAnswers(int roundNumber, RoomId roomId);
    Map<String, Map<String, PlayerAnswer>> calculatePlayerScoreForRound(RoomId roomId, int roundNumber);
    FlaggedAnswer beginVotePhase(RoomId roomId, String targetedPlayer, String triggeredByPlayer, String category, int roundNumber, String answer);
    void submitVote(RoomId roomId,  String category, int roundNumber, String targetPlayer, String voterPlayer, boolean vote);
    VoteRoundResults getVoteRoundResults(RoomId roomId, String category, int roundNumber, String targetPlayer);
    void invalidatePlayerAnswer(RoomId roomId, String targetPlayer, String category, int roundNum);
    Map<PlayerId, Integer> finalizeRoundScores(RoomId roomId, int roundNumber);
    void endRound(RoomId roomId);
}
