package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.CumulativeScoreStore;
import org.barahi.store.gamelogic.GameStateStore;

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
  public void updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> scores) {
    cumulativeScoreStore.updatePlayerScores(roomId, scores);
  }

  @Override
  public int getNumberOfRounds(RoomId roomId){
    return gameSettingsStore.getNumberOfRounds(roomId);
  }


  @Override
  public Map<PlayerId, Integer> endGame(RoomId roomId) {
    return cumulativeScoreStore.getPlayerScores(roomId);
  }
}
