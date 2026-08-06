package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class VoteResultsPayloadJson implements EventPayload {
  @JsonProperty
  private String category;

  @JsonProperty
  private int roundNumber;

  @JsonProperty
  private String targetPlayer;

  @JsonProperty
  private int validAnswerVotes;

  @JsonProperty
  private int invalidAnswerVotes;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public String getTargetPlayer() {
    return targetPlayer;
  }

  public void setTargetPlayer(String targetPlayer) {
    this.targetPlayer = targetPlayer;
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
