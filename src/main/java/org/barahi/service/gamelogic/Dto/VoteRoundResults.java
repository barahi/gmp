package org.barahi.service.gamelogic.Dto;

import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player;

public class VoteRoundResults {
  private CategoryId categoryId;

  private int roundNumber;

  private Player.PlayerId targetPlayerId;

  private int validAnswerVotes;

  private int invalidAnswerVotes;

  public VoteRoundResults(CategoryId categoryId, int roundNumber, Player.PlayerId targetPlayerId, int validAnswerVotes, int invalidAnswerVotes) {
    this.categoryId = categoryId;
    this.roundNumber = roundNumber;
    this.targetPlayerId = targetPlayerId;
    this.validAnswerVotes = validAnswerVotes;
    this.invalidAnswerVotes = invalidAnswerVotes;
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

  public Player.PlayerId getTargetPlayerId() {
    return targetPlayerId;
  }

  public void setTargetPlayerId(Player.PlayerId targetPlayerId) {
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
