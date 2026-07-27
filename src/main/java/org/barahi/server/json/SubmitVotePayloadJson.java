package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class   SubmitVotePayloadJson implements EventPayload {
  @JsonProperty
  String category;

  @JsonProperty
  int roundNumber;

  @JsonProperty
  String targetPlayerId;

  @JsonProperty
  String voterId;

  @JsonProperty
  boolean vote;

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

  public String getTargetPlayerId() {
    return targetPlayerId;
  }

  public void setTargetPlayerId(String targetPlayerId) {
    this.targetPlayerId = targetPlayerId;
  }

  public String getVoterId() {
    return voterId;
  }

  public void setVoterId(String voterId) {
    this.voterId = voterId;
  }

  public boolean getVote() {
    return vote;
  }

  public void setVote(boolean vote) {
    this.vote = vote;
  }
}
