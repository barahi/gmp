package org.barahi.service.gamelogic.Dto;

public class PlayerAnswer {
  private String answer;
  private int points;

  public PlayerAnswer(String answer, int points) {
    this.answer = answer;
    this.points = points;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }
}
