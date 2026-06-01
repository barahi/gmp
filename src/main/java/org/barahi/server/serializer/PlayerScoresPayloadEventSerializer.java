package org.barahi.server.serializer;

import org.barahi.server.json.RoundResultsPayloadJson;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.Map;

public class PlayerScoresPayloadEventSerializer {
  public RoundResultsPayloadJson toJson(Map<PlayerId, Integer> playerScores){
    RoundResultsPayloadJson json = new RoundResultsPayloadJson();
    Map<String, Integer> jsonMap = new HashMap<>();
    playerScores.forEach((player, score) -> {
      jsonMap.put(player.getId().toString(), score);
    });
    json.setPlayerScores(jsonMap);
    return json;
  }
}
