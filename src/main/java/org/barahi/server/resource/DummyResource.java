package org.barahi.server.resource;

import com.google.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.barahi.service.gamelogic.RoundLogicService;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.room.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Path("dummy")
public class DummyResource {

    @GET
    public String getDummy() {
      return  "Hello";

    }
}
