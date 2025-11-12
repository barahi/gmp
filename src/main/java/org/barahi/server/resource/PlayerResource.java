package org.barahi.server.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.barahi.server.json.PlayerJson;
import org.barahi.server.serializer.PlayerSerializer;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerService;

import java.util.UUID;

@Path(PlayerResource.BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerResource {
    public static final String BASE_PATH = "player";

    private final PlayerService playerService;
    private final PlayerSerializer playerSerializer;

    @Inject
    public PlayerResource(
            PlayerService playerService,
            PlayerSerializer playerSerializer
    ) {
        this.playerService = playerService;
        this.playerSerializer = playerSerializer;
    }

    @GET
    public String ping() {
        return "Player resource is alive";
    }

    @GET
    @Path("{id}")
    public PlayerJson getPlayer(@PathParam("id") String id) {
        PlayerId playerId;
        try {
            playerId = new PlayerId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            return null;
        }
        try {
            Player player = playerService.getPlayer(playerId);
            return playerSerializer.toJson(player);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @POST
    public PlayerJson createPlayer(PlayerJson playerJson) {
        Player unsavedPlayer = playerSerializer.fromJson(playerJson);
        Player player = playerService.storePlayer(unsavedPlayer);
        return playerSerializer.toJson(player);
    }
}
