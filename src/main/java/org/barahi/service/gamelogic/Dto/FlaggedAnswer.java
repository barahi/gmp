package org.barahi.service.gamelogic.Dto;

import org.barahi.serviceapi.player.Player.PlayerId;

public class FlaggedAnswer {
  private String category;
  private PlayerId flaggedPlayerId;
  private PlayerId flaggerPlayerId;
  private String answer;
  private int score;

  public FlaggedAnswer(String category, PlayerId flaggedPlayerId, PlayerId flaggerPlayerId, String answer, int score) {
    this.category = category;
    this.flaggedPlayerId = flaggedPlayerId;
    this.flaggerPlayerId = flaggerPlayerId;
    this.answer = answer;
    this.score = score;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public PlayerId getFlaggedPlayerId() {
    return flaggedPlayerId;
  }

  public void setFlaggedPlayerId(PlayerId flaggedPlayerId) {
    this.flaggedPlayerId = flaggedPlayerId;
  }

  public PlayerId getFlaggerPlayerId() {
    return flaggerPlayerId;
  }

  public void setFlaggerPlayerId(PlayerId flaggerPlayerId) {
    this.flaggerPlayerId = flaggerPlayerId;
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
