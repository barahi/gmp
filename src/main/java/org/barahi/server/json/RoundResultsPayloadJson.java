package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

import java.util.Map;

public class RoundResultsPayloadJson implements EventPayload {
  @JsonProperty("roundNumber")
  private int roundNumber;
  @JsonProperty("playerScores")
  private Map<String, Integer> playerScores;

  public RoundResultsPayloadJson(){}

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Map<String, Integer> getPlayerScores() {
    return playerScores;
  }

  public void setPlayerScores(Map<String, Integer> playerScores) {
    this.playerScores = playerScores;
  }
}
