package org.barahi.service.gamelogic;

import java.util.List;
import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {
    void initializeRound(int numberOfRounds, List<String> categories, List<PlayerId> players);
    Map<PlayerId, Integer> calculatePlayerScoreForRound(Map<String, Map<PlayerId, String>> categoryToPlayerAnswers, List<PlayerId> playerIds);

}
