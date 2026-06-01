package org.barahi.server.resource.socket.events.beginvote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.BeginVotePayloadEventJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class BeginVotePhaseEvent implements Event<BeginVotePayloadEventJson> {
  @JsonProperty("payload")
  private BeginVotePayloadEventJson beginVotePayloadEventJson;

  public BeginVotePhaseEvent(BeginVotePayloadEventJson beginVotePayloadEventJson) {
    this.beginVotePayloadEventJson = beginVotePayloadEventJson;
  }

  @JsonProperty("type")
  public String getType() {
    return EventType.BEGIN_VOTE_PHASE.name();
  }

  @Override
  @JsonProperty("payload")
  public BeginVotePayloadEventJson getPayload() {
    return beginVotePayloadEventJson;
  }

  @Override
  @JsonProperty("payload")
  public void setPayload(BeginVotePayloadEventJson payload) {
    this.beginVotePayloadEventJson = payload;
  }
}
