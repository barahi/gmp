package org.barahi.server.serializer;

import org.barahi.server.json.RoundScoreEventPayloadJson;
import org.barahi.server.resource.socket.RoundScoreEventPayload;

import java.util.HashMap;
import java.util.Map;

public class RoundScoreEventPayloadSerializer {
  public RoundScoreEventPayloadJson toJson(RoundScoreEventPayload payload){
    RoundScoreEventPayloadJson json = new RoundScoreEventPayloadJson();
    json.setRoundNumber(payload.getRoundNumber());
    Map<String, Map<String, Integer>> fullScoreMap = new HashMap<>();
    if (payload.getRoundScoreMap() != null){
      payload.getRoundScoreMap().forEach((category, playerScoreMap) -> {
        Map<String, Integer> catScoreMap = new HashMap<>();
        playerScoreMap.forEach((k, v) -> {
          catScoreMap.put(k.getId().toString(), v);
        });
        fullScoreMap.put(category, catScoreMap);
      });
    }
    json.setRoundScoreMap(fullScoreMap);
    return json;
  }
}
