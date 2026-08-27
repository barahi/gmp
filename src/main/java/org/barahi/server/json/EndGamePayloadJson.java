package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

import java.util.List;
import java.util.Map;

public class EndGamePayloadJson implements EventPayload {
  @JsonProperty
  List<String> gameWinner;

  public List<String> getGameWinner() {
    return gameWinner;
  }

  public void setGameWinner(List<String> gameWinner) {
    this.gameWinner = gameWinner;
  }
}
