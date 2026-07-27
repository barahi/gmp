package org.barahi.server.resource.socket.events.timeupevent;

import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventPayload;
import org.barahi.server.resource.socket.EventType;

public class TimeUpEvent implements Event<TimeUpEvent.TimeUpEventPayload> {
  @Override
  public String getType() {
    return EventType.TIME_UP.name();
  }

  @Override
  public TimeUpEventPayload getPayload() {
    return new TimeUpEventPayload();
  }

  @Override
  public void setPayload(TimeUpEventPayload payload) {}

  public static class TimeUpEventPayload implements EventPayload {
    private final String message = "Time is up!";

    public String getMessage() {
      return message;
    }
  }
}
