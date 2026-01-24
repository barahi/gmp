package org.barahi.service.gamelogic;

import org.barahi.infra.Functional;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GameLogicServiceImpl {
    int roundNumber;
    int numberOfRounds;
    List<PlayerId> playerIds;
    Map<PlayerId, Integer> playerScores = new HashMap<>();

    public void initializeGame(){
      playerScores = Functional.createMap(playerIds, Function.identity(), score -> 0);
      int roundNumber = 1;
    }

    public void startNextRound(){
      if (roundNumber < numberOfRounds){
        roundNumber++;
      } else {
        this.endGame();
      }
    }

    public void endRound(){
      RoundLogicService rls = new RoundLogicServiceImpl();
      Map<String, Map<PlayerId, String>> categoryToPlayerAnswers = new HashMap<>();
      // populate categoryToPlayer answers with answers from frontend
      rls.calculatePlayerScoreForRound(categoryToPlayerAnswers, playerIds);
      this.startNextRound();
    }

    public Map<PlayerId, Integer> endGame(){
      return playerScores;
    }
}
