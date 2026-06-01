package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

import java.util.Map;

public class RoundScorePayloadJson implements EventPayload {
  @JsonProperty("roundNumber")
  private int roundNumber;

  @JsonProperty("roundScoreMap")
  private Map<String, Map<String, Integer>> roundScoreMap;

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Map<String, Map<String, Integer>> getRoundScoreMap() {
    return roundScoreMap;
  }

  public void setRoundScoreMap(Map<String, Map<String, Integer>> roundScoreMap) {
    this.roundScoreMap = roundScoreMap;
  }
}
