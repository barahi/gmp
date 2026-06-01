package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

import java.util.Map;

public class RoundResultsPayloadJson implements EventPayload {
  @JsonProperty
  private Map<String, Integer> playerScores;

  public Map<String, Integer> getPlayerScores() {
    return playerScores;
  }

  public void setPlayerScores(Map<String, Integer> playerScores) {
    this.playerScores = playerScores;
  }
}
