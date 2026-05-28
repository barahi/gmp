package org.barahi.server.resource.socket.events.submitvote;

import org.barahi.server.resource.socket.EventPayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

public class SubmitVoteEventPayload implements EventPayload {
  private CategoryId categoryId;
  private int roundNumber;
  private PlayerId targetPlayerId;
  private PlayerId voterId;
  private boolean vote;

  public CategoryId getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(CategoryId categoryId) {
    this.categoryId = categoryId;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public PlayerId getTargetPlayerId() {
    return targetPlayerId;
  }

  public void setTargetPlayerId(PlayerId targetPlayerId) {
    this.targetPlayerId = targetPlayerId;
  }

  public PlayerId getVoterId() {
    return voterId;
  }

  public void setVoterId(PlayerId voterId) {
    this.voterId = voterId;
  }

  public boolean getVote() {
    return vote;
  }

  public void setVote(boolean vote) {
    this.vote = vote;
  }
}
