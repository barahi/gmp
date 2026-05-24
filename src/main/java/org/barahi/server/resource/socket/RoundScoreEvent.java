package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoundScoreEvent implements Event<RoundScoreEventPayload> {
  @JsonProperty("payload")
  private RoundScoreEventPayload roundScoreEventPayload;

  public RoundScoreEvent(){};
  public RoundScoreEvent(RoundScoreEventPayload roundScoreEventPayload) {
    this.roundScoreEventPayload = roundScoreEventPayload;
  }

  @Override
  @JsonProperty("type")
  public String getType() {
    return EventType.ROUND_SCORES.name();
  }

  @Override
  @JsonProperty("payload")
  public RoundScoreEventPayload getPayload() {
    return roundScoreEventPayload;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(RoundScoreEventPayload roundScoreEventPayload) {
    this.roundScoreEventPayload = roundScoreEventPayload;
  }
}
