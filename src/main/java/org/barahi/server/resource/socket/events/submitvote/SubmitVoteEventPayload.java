package org.barahi.server.resource.socket.events.submitvote;

import org.barahi.server.resource.socket.EventPayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

public class SubmitVoteEventPayload implements EventPayload {
  private String category;
  private String targetPlayer;
  private String voterPlayer;
  private boolean vote;

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
