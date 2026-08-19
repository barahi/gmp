package org.barahi.server.resource.socket.events.endgame;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.EndGamePayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class EndGameEvent implements Event<EndGamePayloadJson> {
  @JsonProperty("payload")
  private EndGamePayloadJson endGamePayloadJson;

  public EndGameEvent(){}
  public EndGameEvent(EndGamePayloadJson endGamePayloadJson) {
    this.endGamePayloadJson = endGamePayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.END_GAME.name();
  }

  @Override
  @JsonProperty("payload")
  public EndGamePayloadJson getPayload(){
    return endGamePayloadJson;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(EndGamePayloadJson payload){
    this.endGamePayloadJson = payload;
  }

}
