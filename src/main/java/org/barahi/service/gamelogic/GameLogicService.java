package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

public interface GameLogicService {
  void startGame(RoomId roomId);
  Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, int roundNumber);
  void startNextRound(RoomId roomId);
  Map<PlayerId, Integer> endGame(RoomId roomId);
}
