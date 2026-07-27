package org.barahi.server.resource.socket.events.startround;

import org.barahi.server.resource.socket.EventPayload;

public class StartRoundEventPayload implements EventPayload {
  private char letterForRound;
  private int roundNumber;

  public StartRoundEventPayload(char letterForRound, int roundNumber) {
    this.letterForRound = letterForRound;
    this.roundNumber = roundNumber;
  }

  public char getLetterForRound() {
    return letterForRound;
  }

  public void setLetterForRound(char letterForRound) {
    this.letterForRound = letterForRound;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }
}
