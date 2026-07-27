package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

import java.util.Map;

public class SubmitAnswerPayloadJson implements EventPayload {
  @JsonProperty
  int roundNumber;
  @JsonProperty
  String playerId;
  @JsonProperty
  Map<String, String> roundAnswers;

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public Map<String, String> getRoundAnswers() {
    return roundAnswers;
  }

  public void setRoundAnswers(Map<String, String> roundAnswers) {
    this.roundAnswers = roundAnswers;
  }
}
