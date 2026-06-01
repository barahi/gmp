package org.barahi.server.resource.socket.events.roundscore;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.RoundScorePayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class RoundScoreEvent implements Event<RoundScorePayloadJson> {
  @JsonProperty("payload")
  private RoundScorePayloadJson roundScoreEventJson;

  public RoundScoreEvent(){};
  public RoundScoreEvent(RoundScorePayloadJson roundScoreEventJson) {
    this.roundScoreEventJson = roundScoreEventJson;
  }

  @Override
  @JsonProperty("type")
  public String getType() {
    return EventType.ROUND_SCORES.name();
  }

  @Override
  @JsonProperty("payload")
  public RoundScorePayloadJson getPayload() {
    return roundScoreEventJson;
  }

  @Override
  public void setPayload(RoundScorePayloadJson roundScoreEventJson) {
    this.roundScoreEventJson = roundScoreEventJson;
  }
}
