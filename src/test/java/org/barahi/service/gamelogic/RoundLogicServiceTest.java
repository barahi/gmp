package org.barahi.service.gamelogic;

import org.barahi.serviceapi.player.Player.PlayerId;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundLogicServiceTest {
    RoundLogicService target = new RoundLogicServiceImpl();

    @Test
    public void complexCalculation() {
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

        Map<PlayerId, Integer> result = target.calculatePlayerScoreForRound(bigBoy, playerIds);

        assertEquals(result.size(), 7);
        assertEquals(result.get(p1), 83);
        assertEquals(result.get(p2), 33);
        assertEquals(result.get(p3), 200);
        assertEquals(result.get(p4), 133);
        assertEquals(result.get(p5), 233);
        assertEquals(result.get(p6), 183);
        assertEquals(result.get(p7), 133);
    }
}
