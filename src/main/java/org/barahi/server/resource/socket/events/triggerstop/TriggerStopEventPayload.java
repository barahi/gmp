package org.barahi.server.resource.socket.events.triggerstop;

import org.barahi.server.resource.socket.EventPayload;

public class TriggerStopEventPayload implements EventPayload {
  private String triggeredBy;

  public TriggerStopEventPayload(String triggeredBy) {
    this.triggeredBy = triggeredBy;
  }

  public String getTriggeredBy() {
    return triggeredBy;
  }

  public void setTriggeredBy(String triggeredBy) {
    this.triggeredBy = triggeredBy;
  }
}
