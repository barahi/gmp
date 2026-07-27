package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

import java.util.Map;

public class EndGamePayloadJson implements EventPayload {
  @JsonProperty
  Map<String, Integer> gameScores;

  public Map<String, Integer> getGameScores() {
    return gameScores;
  }

  public void setGameScores(Map<String, Integer> gameScores) {
    this.gameScores = gameScores;
  }
}
