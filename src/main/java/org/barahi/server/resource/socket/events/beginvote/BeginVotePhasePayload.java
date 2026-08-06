package org.barahi.server.resource.socket.events.beginvote;

import org.barahi.server.resource.socket.EventPayload;

public class BeginVotePhasePayload implements EventPayload {
  private String category;
  private String targetedPlayer;
  private String triggeredByPlayer;
  private String answer;

  public BeginVotePhasePayload(String category, String targetedPlayer, String triggeredByPlayer, String answer) {
    this.category = category;
    this.targetedPlayer = targetedPlayer;
    this.triggeredByPlayer = triggeredByPlayer;
    this.answer = answer;
  }

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
}
