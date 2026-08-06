package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class EndVoteRoundPayloadJson implements EventPayload {
  @JsonProperty
  private String category;

  @JsonProperty
  private String targetPlayer;

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
}
