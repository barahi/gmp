package org.barahi.server.serializer;

import org.barahi.server.json.RoundScoreEventPayloadJson;
import org.barahi.server.resource.socket.RoundScoreEventPayload;

public class RoundScoreEventPayloadSerializer {
  public RoundScoreEventPayloadJson toJson(RoundScoreEventPayload payload){
    RoundScoreEventPayloadJson json = new RoundScoreEventPayloadJson();
    json.setCategory(payload.getRoundNumber());
  }
}
