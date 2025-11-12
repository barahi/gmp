package org.barahi.server.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("dummy")
public class DummyResource {
    @GET
    public String getDummy() {
        return "This is a dummy resource";
    }
}
