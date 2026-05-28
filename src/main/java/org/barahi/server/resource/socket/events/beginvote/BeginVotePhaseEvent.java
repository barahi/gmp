package org.barahi.server.resource.socket.events.beginvote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventType;

public class BeginVotePhaseEvent {
  @JsonProperty("type")
  public String getType() {
    return EventType.BEGIN_VOTE_PHASE.name();
  }
}
