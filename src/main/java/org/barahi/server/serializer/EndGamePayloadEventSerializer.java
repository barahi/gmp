package org.barahi.server.serializer;

import org.barahi.server.json.EndGamePayloadJson;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.Map;

public class EndGamePayloadEventSerializer {
  public EndGamePayloadJson toJson(Map<String, Integer> scores){
    EndGamePayloadJson json = new EndGamePayloadJson();
    Map<String, Integer> jsonMap = new HashMap<>(scores);
    json.setGameScores(jsonMap);
    return json;
  }
}
