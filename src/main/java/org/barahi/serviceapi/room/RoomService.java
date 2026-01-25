package org.barahi.serviceapi.room;

public interface RoomService {
    Room createRoom(Room unsavedRoom);

    Room getRoom(Room.RoomId id) throws IllegalAccessException;

    void removeRoom(Room.RoomId id);
}
