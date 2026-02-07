package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.store.gamelogic.CumulativeScoreStore;
import org.barahi.store.gamelogic.GameStateStore;

import java.util.Map;

public class GameLogicServiceImpl implements GameLogicService{

  private final GameStateStore gameStateStore;
  private final CumulativeScoreStore cumulativeScoreStore;
  private final RoundLogicService roundLogicService;


  @Inject
  public GameLogicServiceImpl(GameStateStore gameStateStore, CumulativeScoreStore cumulativeScoreStore, RoundLogicService roundLogicService){
    this.gameStateStore = gameStateStore;
    this.cumulativeScoreStore = cumulativeScoreStore;
    this.roundLogicService = roundLogicService;
  }

  @Override
  public void startGame(String roomId){
    roundLogicService.startRound(roomId, 1);
  }

  @Override
  public Map<PlayerId, Integer> updatePlayerScores(String roomId, int roundNumber) {
    Map<PlayerId, Integer> scores = roundLogicService.calculatePlayerScoreForRound(roomId, roundNumber);
    return cumulativeScoreStore.updatePlayerScores(roomId, scores);
  }

  @Override
  public void startNextRound(String roomId) {
    // TODO: Fetch numberofRounds from GameSettingService - will delete line 42 then
    int numberOfRounds = 7;
    int currRound = gameStateStore.getCurrentRound(roomId);
    if (currRound < numberOfRounds){
      currRound++;
      roundLogicService.startRound(roomId, currRound);
    } else {
      this.endGame(roomId);
    }
  }

  @Override
  public Map<PlayerId, Integer> endGame(String roomId) {
    return cumulativeScoreStore.getPlayerScores(roomId);
  }





}
