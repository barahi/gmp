package org.barahi.service.gamelogic;

import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;
import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {
    char startRound(RoomId roomId, int roundNumber);
    void storeAnswers(RoomId roomId, CategoryId categoryId, int roundNumber, Map<PlayerId, String> playerAnswers);
    Map<PlayerId, Integer> calculatePlayerScoreForRound(RoomId roomId, int roundNumber);
    void beginVotePhase(RoomId roomId);
    void invalidatePlayerAnswer(RoomId roomId, PlayerId playerId, CategoryId category, int roundNum);
    void endRound(RoomId roomId);
}
