package org.barahi.server.resource.socket.events.endround;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.RoundResultsPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class RoundResultsEvent implements Event<RoundResultsPayloadJson> {
  @JsonProperty("payload")
  private RoundResultsPayloadJson roundResultsPayloadJson;

  public RoundResultsEvent(RoundResultsPayloadJson roundResultsPayloadJson) {
    this.roundResultsPayloadJson = roundResultsPayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.ROUND_RESULTS.name();
  }

  @Override
  @JsonProperty("payload")
  public RoundResultsPayloadJson getPayload(){
    return roundResultsPayloadJson;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(RoundResultsPayloadJson payload){
    this.roundResultsPayloadJson = payload;
  }
}
