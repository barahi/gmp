package org.barahi.server.resource.socket.events.beginvote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.BeginVotePayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class BeginVotePhaseEvent implements Event<BeginVotePayloadJson> {
  @JsonProperty("payload")
  private BeginVotePayloadJson beginVotePayloadEventJson;

  public BeginVotePhaseEvent(BeginVotePayloadJson beginVotePayloadEventJson) {
    this.beginVotePayloadEventJson = beginVotePayloadEventJson;
  }

  @JsonProperty("type")
  public String getType() {
    return EventType.BEGIN_VOTE_PHASE.name();
  }

  @Override
  @JsonProperty("payload")
  public BeginVotePayloadJson getPayload() {
    return beginVotePayloadEventJson;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(BeginVotePayloadJson payload) {
    this.beginVotePayloadEventJson = payload;
  }
}
