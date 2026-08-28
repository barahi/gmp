package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class EarlyStopPayloadJson implements EventPayload {
  @JsonProperty
  private String triggeredBy;
  public String getTriggeredBy() {
    return triggeredBy;
  }
  public void setTriggeredBy(String triggeredBy) {
    this.triggeredBy = triggeredBy;
  }
}
