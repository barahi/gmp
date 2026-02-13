package org.barahi.server.resource;

import org.barahi.server.json.JoinRoomJson;
import jakarta.ws.rs.*;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.server.json.RoomCreateJson;
import org.barahi.server.json.RoomJson;
import org.barahi.serviceapi.room.RoomService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


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
    public void addPlayerToRoom(
            @PathParam("roomId") String roomId,
            JoinRoomJson joinRoomJson
    ) {
        try {
          roomService.addPlayerToRoom(roomId, joinRoomJson);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Failed to add player to room");
        }
    }

    @DELETE
    @Path("/{roomId}/")
    public void removeRoom (
        @PathParam("roomId") String roomId
    ) {
        roomService.removeRoomAndAllItsResources(roomId);
    }
}