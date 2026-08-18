package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class  SubmitVotePayloadJson implements EventPayload {
  @JsonProperty
  String category;

  @JsonProperty
  String targetPlayer;

  @JsonProperty
  String voterPlayer;
  @JsonProperty
  boolean vote;

  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }

  public String getTargetPlayer() {
    return targetPlayer;
  }

  public void setTargetPlayer(String targetPlayer) {
    this.targetPlayer = targetPlayer;
  }

  public String getVoterPlayer() {
    return voterPlayer;
  }

  public void setVoterPlayer(String voterPlayer) {
    this.voterPlayer = voterPlayer;
  }

  public boolean getVote() {
    return vote;
  }

  public void setVote(boolean vote) {
    this.vote = vote;
  }
}
