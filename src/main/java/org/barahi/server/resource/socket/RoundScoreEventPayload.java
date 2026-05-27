package org.barahi.server.resource.socket;

import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.Map;

public class RoundScoreEventPayload implements EventPayload {
  private int roundNumber;
  private Map<String, Map<PlayerId, Integer>> roundScoreMap;

  public RoundScoreEventPayload(int roundNumber, Map<String, Map<PlayerId, Integer>> roundScoreMap) {
    this.roundNumber = roundNumber;
    this.roundScoreMap = roundScoreMap;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Map<String, Map<PlayerId, Integer>> getRoundScoreMap() {
    return roundScoreMap;
  }

  public void setRoundScoreMap(Map<String, Map<PlayerId, Integer>> roundScoreMap) {
    this.roundScoreMap = roundScoreMap;
  }
}
