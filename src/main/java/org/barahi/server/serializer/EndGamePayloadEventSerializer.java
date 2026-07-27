package org.barahi.server.serializer;

import org.barahi.server.json.EndGamePayloadJson;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.Map;

public class EndGamePayloadEventSerializer {
  public EndGamePayloadJson toJson(Map<PlayerId, Integer> scores){
    EndGamePayloadJson json = new EndGamePayloadJson();
    Map<String, Integer> jsonMap = new HashMap<>();
    scores.forEach((player, score) -> {
      jsonMap.put(player.getId().toString(), score);
    });
    json.setGameScores(jsonMap);
    return json;
  }
}
