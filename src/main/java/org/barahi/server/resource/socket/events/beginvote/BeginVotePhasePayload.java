package org.barahi.server.resource.socket.events.beginvote;

import org.barahi.server.resource.socket.EventPayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

public class BeginVotePhasePayload implements EventPayload {
  private CategoryId categoryId;
  private int roundNumber;
  private PlayerId targetPlayerId;
  private PlayerId voterId;
  private String answer;

  public BeginVotePhasePayload(CategoryId categoryId, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, String answer) {
    this.categoryId = categoryId;
    this.roundNumber = roundNumber;
    this.targetPlayerId = targetPlayerId;
    this.voterId = voterId;
    this.answer = answer;
  }

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

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
