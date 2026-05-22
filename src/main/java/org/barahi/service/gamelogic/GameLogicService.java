package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.Map;

public interface GameLogicService {
  void initGame(RoomId roomId);
  void updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> scores);
  int getNumberOfRounds(RoomId roomId);
  Map<PlayerId, Integer> endGame(RoomId roomId);
}
