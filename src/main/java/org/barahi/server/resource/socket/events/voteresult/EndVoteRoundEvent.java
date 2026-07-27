package org.barahi.server.resource.socket.events.voteresult;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.EndVoteRoundPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class EndVoteRoundEvent implements Event<EndVoteRoundPayloadJson> {
  @JsonProperty("payload")
  private EndVoteRoundPayloadJson endVoteRoundEventPayloadJson;

  public EndVoteRoundEvent(){}

  public EndVoteRoundEvent(EndVoteRoundPayloadJson endVoteRoundEventPayloadJson){
    this.endVoteRoundEventPayloadJson = endVoteRoundEventPayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.END_VOTE_ROUND.name();
  }

  @Override
  public EndVoteRoundPayloadJson getPayload() {
    return endVoteRoundEventPayloadJson;
  }

  @Override
  public void setPayload(EndVoteRoundPayloadJson payload) {
    this.endVoteRoundEventPayloadJson = payload;
  }
}
