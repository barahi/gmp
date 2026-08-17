package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

public interface GameLogicService {
  void initGame(RoomId roomId);
  Map<String, Integer> updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> finalScores);
  int getRoundDuration(RoomId roomId);
  int getNumberOfRounds(RoomId roomId);
  Map<String, Integer> endGame(RoomId roomId);
}
