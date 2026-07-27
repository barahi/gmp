package org.barahi.server.resource.socket.events.voteresult;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.VoteResultsPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class VoteResultsEvent implements Event<VoteResultsPayloadJson> {
  @JsonProperty("payload")
  private VoteResultsPayloadJson voteResultEventPayloadJson;

  public VoteResultsEvent() {}

  public VoteResultsEvent(VoteResultsPayloadJson voteResultEventPayloadJson){
    this.voteResultEventPayloadJson = voteResultEventPayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.VOTE_RESULTS.name();
  }

  @Override
  @JsonProperty("payload")
  public VoteResultsPayloadJson getPayload(){
    return voteResultEventPayloadJson;
  }

  @Override
  public void setPayload(VoteResultsPayloadJson voteResultEventPayloadJson){
    this.voteResultEventPayloadJson = voteResultEventPayloadJson;
  }
}
