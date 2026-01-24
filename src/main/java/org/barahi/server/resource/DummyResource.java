package org.barahi.server.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.barahi.service.gamelogic.RoundLogicService;
import org.barahi.service.gamelogic.RoundLogicServiceImpl;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("dummy")
public class DummyResource {

    @GET
    public String getDummy() {
        RoundLogicService rls = new RoundLogicServiceImpl();
        PlayerId p1 = PlayerId.newId();
        PlayerId p2 = PlayerId.newId();
        PlayerId p3 = PlayerId.newId();
        PlayerId p4 = PlayerId.newId();
        PlayerId p5 = PlayerId.newId();
        PlayerId p6 = PlayerId.newId();
        PlayerId p7 = PlayerId.newId();

        List<PlayerId> playerIds = new ArrayList<>();
        playerIds.add(p1);
        playerIds.add(p2);
        playerIds.add(p3);
        playerIds.add(p4);
        playerIds.add(p5);
        playerIds.add(p6);
        playerIds.add(p7);


        Map<PlayerId, String> pa1 = new HashMap<>();
        pa1.put(p1, "cat");
        pa1.put(p2, "cat");
        pa1.put(p3, "chameleon");
        pa1.put(p4, "cat");
        pa1.put(p5, "crocodile");
        pa1.put(p6, "camel");
        pa1.put(p7, "camel");

        Map<PlayerId, String> pa2 = new HashMap<>();
        pa2.put(p1, "");
        pa2.put(p2, "");
        pa2.put(p3, "crimson");
        pa2.put(p4, "crimson");
        pa2.put(p5, "cyan");
        pa2.put(p6, "cyan");
        pa2.put(p7, "cyan");

        Map<PlayerId, String> pa3 = new HashMap<>();
        pa3.put(p1, "canada");
        pa3.put(p2, "");
        pa3.put(p3, "colombia");
        pa3.put(p4, "colombia");
        pa3.put(p5, "croatia");
        pa3.put(p6, "camerun");
        pa3.put(p7, "canada");

        Map<String, Map<PlayerId, String>> bigBoy = new HashMap<>();
        bigBoy.put("animals", pa1);
        bigBoy.put("colors", pa2);
        bigBoy.put("countries", pa3);

        return rls.calculatePlayerScoreForRound(bigBoy, playerIds).toString();
    }
}
