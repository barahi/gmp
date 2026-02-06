package org.barahi.service.gamelogic;

import java.util.List;
import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {

    Character startRound(); // generate random letter, create empty category summary ?
    void storeAnswers(Map<String, String> playerAnswers, String category, int roundNumber);

    Map<PlayerId, Integer> calculatePlayerScore();

    void invalidatePlayerAnswer(PlayerId playerId, String category);

    Map<PlayerId, Integer> endRound(); // call gameLogicService.nextRound()
}
