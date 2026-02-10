package org.barahi.server.resource;

import org.barahi.server.json.JoinRoomJson;
import jakarta.ws.rs.*;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.server.json.PlayerJson;
import org.barahi.server.json.RoomCreateJson;
import org.barahi.server.json.RoomJson;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.RoomService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path(RoomsResource.BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomsResource {
    public static final String BASE_PATH = "room";

    private final RoomService roomService;

    @Inject
    public RoomsResource(RoomService roomService) {
        this.roomService = roomService;
    }

    @GET
    public String ping() {
        return "Rooms resource is alive";
    }

    @POST
    public RoomJson createRoom(@Valid RoomCreateJson roomCreateJson) {
        try {
            return roomService.createRoom(roomCreateJson);
        } catch (ObjectNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @POST
    @Path("/{roomId}/join")
    public Response addPlayerToRoom(
            @PathParam("roomId") String roomId,
            @Valid JoinRoomJson joinRoomJson) {
        roomService.addPlayerToRoom(roomId, joinRoomJson);
        return Response.ok().build();
    }

}