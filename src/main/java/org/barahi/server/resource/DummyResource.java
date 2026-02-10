package org.barahi.server.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.barahi.service.gamelogic.RoundLogicService;

@Path("dummy")
public class DummyResource {
    @GET
    public String getDummy() {
      return  "Hello";
    }
}

