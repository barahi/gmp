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

  private final GameStateStore gameStateStore;
  private final CumulativeScoreStore cumulativeScoreStore;
  private final GameSettingsStore gameSettingsStore;
  private final RoundLogicService roundLogicService;
  private final RoomService roomService;


  @Inject
  public GameLogicServiceImpl(GameStateStore gameStateStore, CumulativeScoreStore cumulativeScoreStore, GameSettingsStore gameSettingsStore, RoundLogicService roundLogicService, RoomService roomService){
    this.gameStateStore = gameStateStore;
    this.cumulativeScoreStore = cumulativeScoreStore;
    this.roundLogicService = roundLogicService;
    this.gameSettingsStore = gameSettingsStore;
    this.roomService = roomService;
  }

  @Override
  public void startGame(RoomId roomId){
    List<PlayerId> playerIds = roomService.getPlayerIdsInRoom(roomId);
    cumulativeScoreStore.initializeScores(roomId, playerIds);
    roundLogicService.startRound(roomId, 1);
  }

  @Override
  public Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, int roundNumber) {
    Map<PlayerId, Integer> scores = roundLogicService.calculatePlayerScoreForRound(roomId, roundNumber);
    return cumulativeScoreStore.updatePlayerScores(roomId, scores);
  }

  @Override
  public void startNextRound(RoomId roomId) {
    int numberOfRounds = gameSettingsStore.getNumberOfRounds(roomId);
    int currRound = gameStateStore.getCurrentRound(roomId);
    if (currRound < numberOfRounds){
      currRound++;
      roundLogicService.startRound(roomId, currRound);
    } else {
      endGame(roomId);
    }
  }

  @Override
  public Map<PlayerId, Integer> endGame(RoomId roomId) {
    return cumulativeScoreStore.getPlayerScores(roomId);
  }
}
