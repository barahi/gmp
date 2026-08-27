package org.barahi.infra.exceptions;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public class UsernameAlreadyTakenException extends ClientErrorException {
  public UsernameAlreadyTakenException(String message){
    super(message, Response.Status.CONFLICT);
  }
}
