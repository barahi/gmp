package org.barahi.server.resource.socket.events.submitanswers;


import org.barahi.server.resource.socket.EventPayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.Map;

public class SubmitAnswersEventPayload implements EventPayload {
  private int roundNumber;
  private PlayerId playerId;
  private Map<String, String> roundAnswers;

  public SubmitAnswersEventPayload(int roundNumber, PlayerId playerId, Map<String, String> roundAnswers) {
    this.roundNumber = roundNumber;
    this.playerId = playerId;
    this.roundAnswers = roundAnswers;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public PlayerId getPlayerId() {
    return playerId;
  }

  public void setPlayerId(PlayerId playerId) {
    this.playerId = playerId;
  }

  public Map<String, String> getRoundAnswers() {
    return roundAnswers;
  }

  public void setRoundAnswers(Map<String, String> roundAnswers) {
    this.roundAnswers = roundAnswers;
  }
}
