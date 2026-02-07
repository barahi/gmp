package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.List;
import java.util.Map;

public interface GameLogicService {
  void startGame(String roomId);
  Map<PlayerId, Integer> updatePlayerScores(String roomId, int roundNumber);
  void startNextRound(String roomId);
  Map<PlayerId, Integer> endGame(String roomId);
}
