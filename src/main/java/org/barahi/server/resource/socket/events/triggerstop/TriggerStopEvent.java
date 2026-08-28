package org.barahi.server.resource.socket.events.triggerstop;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class TriggerStopEvent implements Event<TriggerStopEventPayload> {
  private TriggerStopEventPayload triggerStopEventPayload;

  public TriggerStopEvent(){}

  public TriggerStopEvent(TriggerStopEventPayload triggerStopEventPayload) {
    this.triggerStopEventPayload = triggerStopEventPayload;
  }

  @Override
  @JsonProperty("type")
  public String getType() {
    return EventType.TRIGGER_STOP.name();
  }

  @Override
  @JsonProperty("payload")
  public TriggerStopEventPayload getPayload() {
    return triggerStopEventPayload;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(TriggerStopEventPayload payload) {
    this.triggerStopEventPayload = payload;
  }
}
