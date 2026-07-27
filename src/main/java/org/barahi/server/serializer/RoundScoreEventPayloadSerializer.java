package org.barahi.server.serializer;

import org.barahi.server.json.RoundScorePayloadJson;
import org.barahi.server.resource.socket.events.roundscore.RoundScoreEventPayload;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;

import java.util.HashMap;
import java.util.Map;

public class RoundScoreEventPayloadSerializer {
  public RoundScorePayloadJson toJson(RoundScoreEventPayload payload){
    RoundScorePayloadJson json = new RoundScorePayloadJson();
    json.setRoundNumber(payload.getRoundNumber());
    Map<String, Map<String, PlayerAnswer>> fullScoreMap = new HashMap<>();
    if (payload.getRoundScoreMap() != null){
      fullScoreMap.putAll(payload.getRoundScoreMap());
    }
    json.setRoundScoreMap(fullScoreMap);
    return json;
  }
}
