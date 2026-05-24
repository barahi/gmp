package org.barahi.server.resource.socket;

import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.Map;

public class RoundScoreEventPayload implements EventPayload {
  private int roundNumber;
  private Map<PlayerId, Integer> playerScores;

  public RoundScoreEventPayload(int roundNumber, Map<PlayerId, Integer> playerScores) {
    this.roundNumber = roundNumber;
    this.playerScores = playerScores;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Map<PlayerId, Integer> getPlayerScores() {
    return playerScores;
  }

  public void setPlayerScores(Map<PlayerId, Integer> playerScores) {
    this.playerScores = playerScores;
  }
}
