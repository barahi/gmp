package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;

import java.util.Map;

public class RoundScorePayloadJson implements EventPayload {
  @JsonProperty("roundNumber")
  private int roundNumber;

  @JsonProperty("roundScoreMap")
  private Map<String, Map<String, PlayerAnswer>> roundScoreMap;

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Map<String, Map<String, PlayerAnswer>> getRoundScoreMap() {
    return roundScoreMap;
  }

  public void setRoundScoreMap(Map<String, Map<String, PlayerAnswer>> roundScoreMap) {
    this.roundScoreMap = roundScoreMap;
  }
}
