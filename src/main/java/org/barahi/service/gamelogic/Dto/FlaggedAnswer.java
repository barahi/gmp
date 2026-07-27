package org.barahi.service.gamelogic.Dto;

import org.barahi.serviceapi.player.Player.PlayerId;

public class FlaggedAnswer {
  private String category;
  private String targetedPlayer;
  private String triggeredByPlayer;
  private String answer;
  private int score;

  public FlaggedAnswer(String category, String targetedPlayer, String triggeredByPlayer, String answer, int score) {
    this.category = category;
    this.targetedPlayer = targetedPlayer;
    this.triggeredByPlayer = triggeredByPlayer;
    this.answer = answer;
    this.score = score;
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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
