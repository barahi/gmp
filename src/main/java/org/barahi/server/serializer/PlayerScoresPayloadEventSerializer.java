package org.barahi.server.serializer;

import org.barahi.server.json.RoundResultsPayloadJson;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.Map;

public class PlayerScoresPayloadEventSerializer {
  public RoundResultsPayloadJson toJson(int roundNumber, Map<String, Integer> playerScores){
    RoundResultsPayloadJson json = new RoundResultsPayloadJson();
    Map<String, Integer> jsonMap = new HashMap<>(playerScores);
    json.setRoundNumber(roundNumber);
    json.setPlayerScores(jsonMap);
    return json;
  }
}
