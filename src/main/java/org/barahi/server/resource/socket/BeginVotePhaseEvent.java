package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeginVotePhaseEvent {
  @JsonProperty("type")
  public String getType() {
    return EventType.BEGIN_VOTE_PHASE.name();
  }
}
