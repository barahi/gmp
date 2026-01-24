package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.Map;

public interface GameLogicService {
  void initializeGame();
  void startNextRound();
  void endRound();
  Map<PlayerId, Integer> endGame();
}
