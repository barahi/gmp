package org.barahi.server.resource.socket.events.earlystop;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.EarlyStopPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class EarlyStopEvent implements Event<EarlyStopPayloadJson> {
  @JsonProperty("payload")
  EarlyStopPayloadJson earlyStopPayloadJson;
  public EarlyStopEvent(){}

  public EarlyStopEvent(EarlyStopPayloadJson payload) {
    this.earlyStopPayloadJson = payload;
  }

  @Override
  @JsonProperty("type")
  public String getType() {
    return EventType.EARLY_STOP.name();
  }

  @Override
  @JsonProperty("payload")
  public EarlyStopPayloadJson getPayload() {
    return earlyStopPayloadJson;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(EarlyStopPayloadJson json) {
    this.earlyStopPayloadJson = json;
  }
}
