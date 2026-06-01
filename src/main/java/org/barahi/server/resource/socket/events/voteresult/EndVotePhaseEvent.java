package org.barahi.server.resource.socket.events.voteresult;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.EndVotePhasePayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class EndVotePhaseEvent implements Event<EndVotePhasePayloadJson> {
  @JsonProperty("payload")
  private EndVotePhasePayloadJson endVoteRoundEventPayloadJson;

  public EndVotePhaseEvent(EndVotePhasePayloadJson endVoteRoundEventPayloadJson){
    this.endVoteRoundEventPayloadJson = endVoteRoundEventPayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType(){
    return EventType.END_VOTE_PHASE.name();
  }

  @Override
  public EndVotePhasePayloadJson getPayload() {
    return endVoteRoundEventPayloadJson;
  }

  @Override
  public void setPayload(EndVotePhasePayloadJson payload) {
    this.endVoteRoundEventPayloadJson = payload;
  }
}
