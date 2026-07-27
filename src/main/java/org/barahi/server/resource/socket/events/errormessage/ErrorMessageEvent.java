package org.barahi.server.resource.socket.events.errormessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventPayload;
import org.barahi.server.resource.socket.EventType;

public class ErrorMessageEvent implements Event<ErrorMessageEvent.ErrorMessageEventPayload> {
  private ErrorMessageEventPayload errorMessageEventPayload;
  public ErrorMessageEvent(ErrorMessageEventPayload errorMessageEventPayload){
    this.errorMessageEventPayload = errorMessageEventPayload;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.ERROR_MESSAGE.name();
  }

  @Override
  @JsonProperty("payload")
  public ErrorMessageEventPayload getPayload(){
    return errorMessageEventPayload;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(ErrorMessageEventPayload payload){
    this.errorMessageEventPayload = payload;
  }

  public static class ErrorMessageEventPayload implements EventPayload{
    private final String message;

    public ErrorMessageEventPayload(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }

}
