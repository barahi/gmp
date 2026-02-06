package org.barahi.service.gamelogic;

import jakarta.inject.Inject;
import org.barahi.generated.tables.CumulativeScore;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.store.gamelogic.CumulativeScoreStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;

import java.util.List;
import java.util.Map;

public class GameLogicServiceImpl implements GameLogicService{

  private final GameStateStore gameStateStore;
  private final CumulativeScoreStore cumulativeScoreStore;
  private final PlayerAnswerStore playerAnswerStore;


  @Inject
  public GameLogicServiceImpl(GameStateStore gameStateStore, CumulativeScoreStore cumulativeScoreStore, PlayerAnswerStore playerAnswerStore){
    this.gameStateStore = gameStateStore;
    this.cumulativeScoreStore = cumulativeScoreStore;
    this.playerAnswerStore = playerAnswerStore;
  }

  @Override
  public void startGame(String roomId){
    // TODO: Fetch List<PlayerId> playerIdList, List<String> categories,List<Character> excludedLetters from GameSettingsService(String roomId);
    // playerAnswerStore.initializePlayerAnswer(roomId, playerIds);
    gameStateStore.initializeGameState(roomId);
  }

  @Override
  public Map<PlayerId, Integer> updatePlayerScores(String roomId, Map<PlayerId, Integer> prevRoundScoreMap) {
    return cumulativeScoreStore.updatePlayerScores(roomId, prevRoundScoreMap);
  }

  @Override
  public void startNextRound(String roomId) {
    // TODO: Fetch numberofRounds from GameSettingService - will delete line 42 then
    int numberOfRounds = 7;
    int currRound = gameStateStore.getCurrentRound(roomId);
    if (currRound < numberOfRounds){
      gameStateStore.changeGameRound(roomId, currRound++);
    } else {
      this.endGame(roomId);
    }
  }

  @Override
  public Map<PlayerId, Integer> endGame(String roomId) {
    return cumulativeScoreStore.getPlayerScores(roomId);
  }





}
