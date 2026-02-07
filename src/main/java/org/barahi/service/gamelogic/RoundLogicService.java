package org.barahi.service.gamelogic;

import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {
    char startRound(RoomId roomId, int roundNumber);
    void storeAnswers(RoomId roomId, Map<PlayerId, String> playerAnswers, String category, int roundNumber);
    Map<PlayerId, Integer> calculatePlayerScoreForRound(RoomId roomId, int roundNumber);
    void beginVotePhase(RoomId roomId);
    void invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, String category);
    void endRound(RoomId roomId);
}
