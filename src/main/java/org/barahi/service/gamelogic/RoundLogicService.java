package org.barahi.service.gamelogic;

import java.util.List;
import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {

    char startRound(String roomId, int roundNumber);
    void storeAnswers(String roomId, Map<PlayerId, String> playerAnswers, String category, int roundNumber);
    Map<PlayerId, Integer> calculatePlayerScoreForRound(String roomId, int roundNumber);
    void invalidatePlayerAnswer(String roomId, PlayerId playerId, String category);
    void endRound(String roomId);
}
