package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class FlaggedAnswerPayloadJson implements EventPayload {
  @JsonProperty
  String category;
  @JsonProperty
  String targetedPlayer;

  @JsonProperty
  String triggeredByPlayer;

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

  public String getTargetedPlayer() {
    return targetedPlayer;
  }

  public void setTargetedPlayer(String targetedPlayer) {
    this.targetedPlayer = targetedPlayer;
  }

  public String getTriggeredByPlayer() {
    return triggeredByPlayer;
  }

  public void setTriggeredByPlayer(String triggeredByPlayer) {
    this.triggeredByPlayer = triggeredByPlayer;
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
