package org.barahi.service.gamelogic.Dto;

import org.barahi.serviceapi.player.Player.PlayerId;

public class VoteRoundResults {
  private String category;

  private int roundNumber;

  private PlayerId targetPlayerId;

  private int validAnswerVotes;

  private int invalidAnswerVotes;

  public VoteRoundResults(String category, int roundNumber, PlayerId targetPlayerId, int validAnswerVotes, int invalidAnswerVotes) {
    this.category = category;
    this.roundNumber = roundNumber;
    this.targetPlayerId = targetPlayerId;
    this.validAnswerVotes = validAnswerVotes;
    this.invalidAnswerVotes = invalidAnswerVotes;
  }

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

  public PlayerId getTargetPlayerId() {
    return targetPlayerId;
  }

  public void setTargetPlayerId(PlayerId targetPlayerId) {
    this.targetPlayerId = targetPlayerId;
  }

  public int getValidAnswerVotes() {
    return validAnswerVotes;
  }

  public void setValidAnswerVotes(int validAnswerVotes) {
    this.validAnswerVotes = validAnswerVotes;
  }

  public int getInvalidAnswerVotes() {
    return invalidAnswerVotes;
  }

  public void setInvalidAnswerVotes(int invalidAnswerVotes) {
    this.invalidAnswerVotes = invalidAnswerVotes;
  }
}
