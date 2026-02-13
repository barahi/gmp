package org.barahi.infra.exceptions;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public class RoomFullException extends ClientErrorException {
    public RoomFullException() {
        super(Response.Status.CONFLICT);
    }
}
