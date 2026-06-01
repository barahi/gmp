package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class VoteResultEventPayloadJson implements EventPayload {
  @JsonProperty
  private String categoryId;

  @JsonProperty
  private int roundNumber;

  @JsonProperty
  private String targetPlayerId;

  @JsonProperty
  private int validAnswerVotes;

  @JsonProperty
  private int invalidAnswerVotes;

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public String getTargetPlayerId() {
    return targetPlayerId;
  }

  public void setTargetPlayerId(String targetPlayerId) {
    this.targetPlayerId = targetPlayerId;
  }

  public int getValidAnswerVotes() {
    return validAnswerVotes;
  }

  public void setValidAnswerVotes(int validAnswerVotes) {
    this.validAnswerVotes = validAnswerVotes;
  }

  public int getInvalidAnswerVotes() {
    return invalidAnswerVotes;
  }

  public void setInvalidAnswerVotes(int invalidAnswerVotes) {
    this.invalidAnswerVotes = invalidAnswerVotes;
  }
}
