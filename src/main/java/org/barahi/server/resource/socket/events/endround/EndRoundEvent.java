package org.barahi.server.resource.socket.events.endround;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.EndRoundPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class EndRoundEvent implements Event<EndRoundPayloadJson> {
  @JsonProperty("payload")
  private EndRoundPayloadJson endRoundPayloadJson;

  public EndRoundEvent(EndRoundPayloadJson endRoundPayloadJson) {
    this.endRoundPayloadJson = endRoundPayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.END_ROUND.name();
  }

  @Override
  @JsonProperty("payload")
  public EndRoundPayloadJson getPayload(){
    return endRoundPayloadJson;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(EndRoundPayloadJson payload){
    this.endRoundPayloadJson = payload;
  }
}
