package org.barahi.infra.exceptions;

public class RoomFullException extends RuntimeException {
    public RoomFullException(String roomId) {
        super("Room " + roomId + " is full");
    }
}
