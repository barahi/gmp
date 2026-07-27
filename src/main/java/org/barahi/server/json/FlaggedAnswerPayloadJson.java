package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class FlaggedAnswerPayloadJson implements EventPayload {
  @JsonProperty
  String category;
  @JsonProperty
  String flaggedPlayerId;

  @JsonProperty
  String flaggerPlayerId;

  @JsonProperty
  String answer;

  @JsonProperty
  int score;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getFlaggedPlayerId() {
    return flaggedPlayerId;
  }

  public void setFlaggedPlayerId(String flaggedPlayerId) {
    this.flaggedPlayerId = flaggedPlayerId;
  }

  public String getFlaggerPlayerId() {
    return flaggerPlayerId;
  }

  public void setFlaggerPlayerId(String flaggerPlayerId) {
    this.flaggerPlayerId = flaggerPlayerId;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
