package org.barahi.service.gamelogic;

import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.gameSettings.GameSettings.GameSettingsId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundLogicServiceImplTest {

  @Mock
  RoomService roomService;

  @Mock
  PlayerAnswerStore playerAnswerStore;

  @Mock
  GameStateStore gameStateStore;

  @Mock
  GameSettingsStore gameSettingsStore;

  @Mock
  GameLogicService gameLogicService;


  @InjectMocks
  RoundLogicServiceImpl roundLogicService;

  @Test
  public void startRound_returnsRandomLetterNotInExcludedLetters() {
    Room.RoomId roomId = new Room.RoomId(UUID.randomUUID());
    GameSettingsId gameSettingsId = new GameSettingsId(UUID.randomUUID());
    List<Character> excludedList = List.of('x','y');
    when(gameSettingsStore.getGameSettingsId(roomId)).thenReturn(gameSettingsId.getId().toString());
    when(gameSettingsStore.getLetterExclusions(gameSettingsId)).thenReturn(excludedList);

    char result = roundLogicService.startRound(roomId, 1);
  }


  @Test
  public void storeAnswersTest(){
    Room.RoomId roomId = new Room.RoomId(UUID.randomUUID());
    CategoryId categoryId = CategoryId.of(UUID.randomUUID());
    int roundNumber = 2;
    PlayerId p1 = PlayerId.newId();
    PlayerId p2 = PlayerId.newId();
    Map<PlayerId, String> playerAnswers = Map.of(p1, "Ant", p2, "Aardvark");
    String gameSettingsId = "settings-123";

    when(gameSettingsStore.getGameSettingsId(roomId)).thenReturn(gameSettingsId);

    roundLogicService.storeAnswers(roomId, categoryId, roundNumber, playerAnswers);

    verify(gameStateStore).changeGamePhase(roomId, RoundPhase.SUBMIT);
    verify(gameSettingsStore).getGameSettingsId(roomId);

    verify(playerAnswerStore).storeAnswers(gameSettingsId, categoryId, roundNumber, playerAnswers);

    verifyNoMoreInteractions(gameStateStore, gameSettingsStore, playerAnswerStore);
  }

    @Test
    public void calculatePlayerScoreForRoundTest() {
        Room.RoomId roomId = new Room.RoomId(UUID.randomUUID());
        int roundNum = 3;
        PlayerId p1 = PlayerId.newId();
        PlayerId p2 = PlayerId.newId();
        PlayerId p3 = PlayerId.newId();
        PlayerId p4 = PlayerId.newId();
        PlayerId p5 = PlayerId.newId();
        PlayerId p6 = PlayerId.newId();
        PlayerId p7 = PlayerId.newId();

        List<PlayerId> playerIds = List.of(p1, p2, p3, p4, p5, p6, p7);

        when(roomService.getPlayerIdsInRoom(roomId)).thenReturn(playerIds);

        Map<String, Map<PlayerId, String>> bigBoy = Map.of(
          "category1", Map.of(
            p1, "cat",
            p2, "cat",
            p3, "chameleon",
            p4, "cat",
            p5, "crocodile",
            p6, "camel",
            p7, "camel"
          ),
          "category2", Map.of(
            p1, "",
            p2, "",
            p3, "crimson",
            p4, "crimson",
            p5, "cyan",
            p6, "cyan",
            p7, "cyan"),
          "category3", Map.of(
            p1, "canada",
            p2, "",
            p3, "colombia",
            p4, "colombia",
            p5, "croatia",
            p6, "cameroon",
            p7, "canada")
        );

        when(playerAnswerStore.getAnswersForRound(playerIds,roundNum)).thenReturn(bigBoy);

        Map<PlayerId, Integer> result = roundLogicService.calculatePlayerScoreForRound(roomId, roundNum);

        assertEquals(result.size(), 7);
        assertEquals(result.get(p1), 83);
        assertEquals(result.get(p2), 33);
        assertEquals(result.get(p3), 200);
        assertEquals(result.get(p4), 133);
        assertEquals(result.get(p5), 233);
        assertEquals(result.get(p6), 183);
        assertEquals(result.get(p7), 133);
    }


  @Test
  public void calculatePlayerScoreForRoundTestWithNulls() {
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());
    int roundNum = 5;

    PlayerId p1 = PlayerId.newId();
    PlayerId p2 = PlayerId.newId();
    PlayerId p3 = PlayerId.newId();
    PlayerId p4 = PlayerId.newId();
    List<PlayerId> playerIds = List.of(p1, p2, p3, p4);

    Map<String, Map<PlayerId, String>> bigBoy = Map.of(
      "category1", Map.of(
        p1, "", // 0
        p2, "cat", // 50
        p3, "chameleon", //100
        p4, "cat" //50
      ),
      "category2", Map.of(
        p1, "", // 0
        p2, "", // 0
        p3, "crimson", // 50
        p4, "crimson"), // 50

      "category3", Map.of(
        p1, "canada", // 100
        p2, "", // 0
        p3, "colombia", // 50
        p4, "colombia") // 50
      // P1 -> 100
      // P2 -> 50
      // P3 -> 200
      // P4 -> 150
    );
    when(roomService.getPlayerIdsInRoom(roomId)).thenReturn(playerIds);
    when(playerAnswerStore.getAnswersForRound(playerIds,roundNum)).thenReturn(bigBoy);

    Map<PlayerId, Integer> result = roundLogicService.calculatePlayerScoreForRound(roomId, roundNum);

    assertEquals(result.size(), 4);
    assertEquals(result.get(p1), 100);
    assertEquals(result.get(p2), 50);
    assertEquals(result.get(p3), 200);
    assertEquals(result.get(p4), 150);
  }


  @Test
  public void beginVotePhaseTest() {
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());
    roundLogicService.beginVotePhase(roomId);
    verify(gameStateStore).changeGamePhase(roomId,RoundPhase.VOTE);
    verifyNoMoreInteractions(gameStateStore);
  }
  @Test
  public void invalidatePlayerAnswerTest(){
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());
    PlayerId p1 = PlayerId.newId();
    CategoryId categoryId = CategoryId.newId();
    int roundNum = 4;

    roundLogicService.invalidatePlayerAnswer(roomId, p1, categoryId, roundNum);
    verify(playerAnswerStore).updateScoreForAnswer(p1, categoryId, roundNum, 0);
    verifyNoMoreInteractions(playerAnswerStore);
  }

  @Test
  public void endRoundTest(){
    Room.RoomId roomId = Room.RoomId.of(UUID.randomUUID());

    roundLogicService.endRound(roomId);
    InOrder inOrder = inOrder(gameStateStore, gameLogicService);
    inOrder.verify(gameStateStore).changeGamePhase(roomId, RoundPhase.SCORE);
    inOrder.verify(gameLogicService).startNextRound(roomId);
    verifyNoMoreInteractions(gameStateStore, gameLogicService);
  }
}
