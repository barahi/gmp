package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.RoundScoreEventPayloadJson;

public class RoundScoreEvent implements Event<RoundScoreEventPayloadJson> {
  @JsonProperty("payload")
  private RoundScoreEventPayloadJson roundScoreEventJson;

  public RoundScoreEvent(){};
  public RoundScoreEvent(RoundScoreEventPayloadJson roundScoreEventJson) {
    this.roundScoreEventJson = roundScoreEventJson;
  }

  @Override
  @JsonProperty("type")
  public String getType() {
    return EventType.ROUND_SCORES.name();
  }

  @Override
  @JsonProperty("payload")
  public RoundScoreEventPayloadJson getPayload() {
    return roundScoreEventJson;
  }

  @Override
  public void setPayload(RoundScoreEventPayloadJson roundScoreEventJson) {
    this.roundScoreEventJson = roundScoreEventJson;
  }
}
