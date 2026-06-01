package org.barahi.server.resource.socket.events.voteresult;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.VoteResultEventPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class VoteResultEvent implements Event<VoteResultEventPayloadJson> {
  @JsonProperty("payload")
  private VoteResultEventPayloadJson voteResultEventPayloadJson;

  public VoteResultEvent() {}

  public VoteResultEvent(VoteResultEventPayloadJson voteResultEventPayloadJson){
    this.voteResultEventPayloadJson = voteResultEventPayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.VOTE_RESULT.name();
  }

  @Override
  @JsonProperty("payload")
  public VoteResultEventPayloadJson getPayload(){
    return this.voteResultEventPayloadJson;
  }

  @Override
  public void setPayload(VoteResultEventPayloadJson voteResultEventPayloadJson){
    this.voteResultEventPayloadJson = voteResultEventPayloadJson;
  }
}
