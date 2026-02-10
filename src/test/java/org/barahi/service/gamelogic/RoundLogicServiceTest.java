package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.GameSettingsStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class RoundLogicServiceTest {
  RoundLogicService target;

  @BeforeEach
  public void setup() {
    RoomService roomService = mock(RoomService.class);
    PlayerAnswerStore playerAnswerStore = mock(PlayerAnswerStore.class);
    GameStateStore gameStateStore = mock(GameStateStore.class);
    GameLogicService gameLogicService = mock(GameLogicService.class);
    GameSettingsStore gameSettingsStore = mock(GameSettingsStore.class);

    target = new RoundLogicServiceImpl(
      roomService,
      playerAnswerStore,
      gameStateStore,
      gameLogicService,
      gameSettingsStore
    );
  }
    @Test
    public void complexCalculation() {
        Room.RoomId roomId = new Room.RoomId(new UUID(8, 7));
        int roundNum = 8;

        PlayerId p1 = PlayerId.newId();
        PlayerId p2 = PlayerId.newId();
        PlayerId p3 = PlayerId.newId();
        PlayerId p4 = PlayerId.newId();
        PlayerId p5 = PlayerId.newId();
        PlayerId p6 = PlayerId.newId();
        PlayerId p7 = PlayerId.newId();

        List<PlayerId> playerIds = List.of(p1, p2, p3, p4, p5, p6, p7);

        Map<String, Map<PlayerId, String>> bigBoy = Map.of(
          "animals", Map.of(
            p1, "cat",
            p2, "cat",
            p3, "chameleon",
            p4, "cat",
            p5, "crocodile",
            p6, "camel",
            p7, "camel"
          ),
          "colors", Map.of(
            p1, "",
            p2, "",
            p3, "crimson",
            p4, "crimson",
            p5, "cyan",
            p6, "cyan",
            p7, "cyan"),
          "countries", Map.of(
            p1, "canada",
            p2, "",
            p3, "colombia",
            p4, "colombia",
            p5, "croatia",
            p6, "cameroon",
            p7, "canada")
        );

        Map<PlayerId, Integer> result = target.calculatePlayerScoreForRound(roomId, roundNum, playerIds, bigBoy);

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
  public void complexCalculationWithNulls() {
    Room.RoomId roomId = new Room.RoomId(new UUID(8, 7));
    int roundNum = 5;

    PlayerId p1 = PlayerId.newId();
    PlayerId p2 = PlayerId.newId();
    PlayerId p3 = PlayerId.newId();
    PlayerId p4 = PlayerId.newId();


    List<PlayerId> playerIds = List.of(p1, p2, p3, p4);

    Map<String, Map<PlayerId, String>> bigBoy = Map.of(
      "animals", Map.of(
        p1, "", // 0
        p2, "cat", // 50
        p3, "chameleon", //100
        p4, "cat" //50
      ),
      "colors", Map.of(
        p1, "", // 0
        p2, "", // 0
        p3, "crimson", // 50
        p4, "crimson"), // 50

      "countries", Map.of(
        p1, "canada", // 100
        p2, "", // 0
        p3, "colombia", // 50
        p4, "colombia") // 50
      // P1 -> 100
      // P2 -> 50
      // P3 -> 200
      // P4 -> 150
    );

    Map<PlayerId, Integer> result = target.calculatePlayerScoreForRound(roomId, roundNum, playerIds, bigBoy);

    assertEquals(result.size(), 4);
    assertEquals(result.get(p1), 100);
    assertEquals(result.get(p2), 50);
    assertEquals(result.get(p3), 200);
    assertEquals(result.get(p4), 150);

  }
}
