package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class RoundScoreEventPayloadJson {
  @JsonProperty
  int roundNumber;

  @JsonProperty
  String category;

  @JsonProperty
  Map<String, Integer> playerScores;

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Map<String, Integer> getPlayerScores() {
    return playerScores;
  }

  public void setPlayerScores(Map<String, Integer> playerScores) {
    this.playerScores = playerScores;
  }
}
