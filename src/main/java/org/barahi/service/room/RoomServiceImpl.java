package org.barahi.service.room;

import java.time.Instant;
import java.util.UUID;

import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.Room.RoomStatus;
import org.barahi.serviceapi.room.RoomImpl;
import org.barahi.serviceapi.room.RoomService;
import org.barahi.store.RoomStore;

import jakarta.inject.Inject;

public class RoomServiceImpl implements RoomService {
    private final RoomStore roomStore;

    @Inject
    public RoomServiceImpl(RoomStore roomStore) {
        this.roomStore = roomStore;
    }

    @Override
    public Room createRoom(Room unsavedRoom) {
        return roomStore.storeRoom(unsavedRoom);
    }

    @Override
    public Room getRoom(Room.RoomId id) throws IllegalAccessException {
        return roomStore.getRoom(id);
    }

    @Override
    public void removeRoom(Room.RoomId id) {
        roomStore.removeRoom(id);
    }

    public Room createRoomFromLobby(UUID hostPlayerId, UUID[] playerIds, Integer maxPlayers,
                                     Integer roundCount, String language) {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        Room unsavedRoom = new RoomImpl(
                id,
                hostPlayerId,
                playerIds,
                maxPlayers,
                roundCount,
                language,
                RoomStatus.WAITING,
                0,
                now,
                now
        );

        return roomStore.storeRoom(unsavedRoom);
    }
}
