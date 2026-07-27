package org.barahi.server.resource.socket.events.roundscore;

import org.barahi.server.resource.socket.EventPayload;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.Map;

public class RoundScoreEventPayload implements EventPayload {
  private int roundNumber;
  private Map<String, Map<String, PlayerAnswer>> roundScoreMap;

  public RoundScoreEventPayload(int roundNumber, Map<String, Map<String, PlayerAnswer>> roundScoreMap) {
    this.roundNumber = roundNumber;
    this.roundScoreMap = roundScoreMap;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Map<String, Map<String, PlayerAnswer>> getRoundScoreMap() {
    return roundScoreMap;
  }

  public void setRoundScoreMap(Map<String, Map<String, PlayerAnswer>> roundScoreMap) {
    this.roundScoreMap = roundScoreMap;
  }
}
