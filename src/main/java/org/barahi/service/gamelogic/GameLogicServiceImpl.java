package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.CumulativeScoreStore;

import java.util.List;
import java.util.Map;

public class GameLogicServiceImpl implements GameLogicService{

  private final CumulativeScoreStore cumulativeScoreStore;
  private final GameSettingsStore gameSettingsStore;
  private final RoomService roomService;


  @Inject
  public GameLogicServiceImpl(CumulativeScoreStore cumulativeScoreStore, GameSettingsStore gameSettingsStore, RoomService roomService){
    this.cumulativeScoreStore = cumulativeScoreStore;
    this.gameSettingsStore = gameSettingsStore;
    this.roomService = roomService;
  }

  @Override
  public void initGame(RoomId roomId){
    List<PlayerId> playerIds = roomService.getPlayerIdsInRoom(roomId);
    cumulativeScoreStore.initializeScores(roomId, playerIds);
  }
  @Override
  public Map<String, Integer> updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> finalScores) {
    cumulativeScoreStore.updatePlayerScores(roomId, finalScores);
    return cumulativeScoreStore.getPlayerScores(roomId);
  }

  @Override
  public int getRoundDuration(RoomId roomId){
    return gameSettingsStore.getRoundDuration(roomId);
  }
  @Override
  public int getNumberOfRounds(RoomId roomId){
    return gameSettingsStore.getNumberOfRounds(roomId);
  }


  @Override
  public Map<String, Integer> endGame(RoomId roomId) {
    return cumulativeScoreStore.getPlayerScores(roomId);
  }
}
