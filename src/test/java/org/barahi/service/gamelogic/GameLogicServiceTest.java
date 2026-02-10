package org.barahi.service.gamelogic;

import org.barahi.service.room.RoomServiceImpl;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.CumulativeScoreStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameLogicServiceTest {

  @Mock
  GameStateStore gameStateStore;

  @Mock
  CumulativeScoreStore cumulativeScoreStore;

  @Mock
  GameSettingsStore gameSettingsStore;

  @Mock
  RoomServiceImpl roomService;
  @Mock
  RoundLogicService roundLogicService;

  @InjectMocks
  GameLogicServiceImpl gameLogicService;


  @Test
  void startGameTest() {
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());
    PlayerId p1 = PlayerId.newId();
    PlayerId p2 = PlayerId.newId();
    PlayerId p3 = PlayerId.newId();
    List<PlayerId> playerIds = List.of(p1, p2, p3);

    when(roomService.getPlayerIdsInRoom(roomId)).thenReturn(playerIds);

    gameLogicService.startGame(roomId);

    verify(cumulativeScoreStore).initializeScores(roomId, playerIds);
    verify(roundLogicService).startRound(roomId, 1);
    verifyNoMoreInteractions(gameStateStore, cumulativeScoreStore, roundLogicService, roomService, gameSettingsStore);
  }

  @Test
  public void updatePlayerScoreTest(){
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());
    int roundNum = 2;
    PlayerId p1 = PlayerId.newId();
    PlayerId p2 = PlayerId.newId();
    PlayerId p3 = PlayerId.newId();

    Map<PlayerId, Integer> roundScores = Map.of(p1, 250, p2, 350, p3, 400);
    Map<PlayerId, Integer> cumulativeScores = Map.of(p1, 1000, p2, 975, p3, 1200);

    when(roundLogicService.calculatePlayerScoreForRound(roomId, roundNum)).thenReturn(roundScores);
    when(cumulativeScoreStore.updatePlayerScores(roomId, roundScores)).thenReturn(cumulativeScores);

    Map<PlayerId, Integer> result =
      gameLogicService.updatePlayerScores(roomId, roundNum);

    assertEquals(cumulativeScores, result);

    verify(roundLogicService)
      .calculatePlayerScoreForRound(roomId, roundNum);

    verify(cumulativeScoreStore)
      .updatePlayerScores(roomId, roundScores);

    verifyNoMoreInteractions(roundLogicService, cumulativeScoreStore);
  }

  @Test
  public void startNewRoundTest(){
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());
    int currRound = 6;
    int numberOfRound = 7;
    when(gameSettingsStore.getNumberOfRounds(roomId)).thenReturn(numberOfRound);
    when(gameStateStore.getCurrentRound(roomId)).thenReturn(currRound);

    gameLogicService.startNextRound(roomId);
    verify(roundLogicService).startRound(roomId, currRound+1);

    verifyNoMoreInteractions(roundLogicService);
    verify(gameSettingsStore).getNumberOfRounds(roomId);
    verify(gameStateStore).getCurrentRound(roomId);
  }

  @Test
  public void startNewRoundTestWithEndGame(){
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());
    int currRound = 7;
    int numberOfRound = 7;
    when(gameSettingsStore.getNumberOfRounds(roomId)).thenReturn(numberOfRound);
    when(gameStateStore.getCurrentRound(roomId)).thenReturn(currRound);

    // using spy to intercept calls within methods
    GameLogicServiceImpl spy = spy(gameLogicService);
    spy.startNextRound(roomId);

    verify(spy).endGame(roomId);
    verifyNoMoreInteractions(roundLogicService);
  }

}
