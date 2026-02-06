package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.List;
import java.util.Map;

public interface GameLogicService {
  void startGame(String roomId, List<PlayerId> playerIdList);

  Map<String, Integer> updatePlayerScores(String roomId, Map<PlayerId, Integer> prevRoundScoreMap);

  void startNextRound(String roomId);
}
