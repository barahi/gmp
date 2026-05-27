package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

import java.util.Map;

public class SubmitAnswerPayloadEventJson implements EventPayload {
  @JsonProperty
  String categoryId;
  @JsonProperty
  int roundNumber;
  @JsonProperty
  Map<String, String> playerAnswers;

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public Map<String, String> getPlayerAnswers() {
    return playerAnswers;
  }

  public void setPlayerAnswers(Map<String, String> playerAnswers) {
    this.playerAnswers = playerAnswers;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }


}
