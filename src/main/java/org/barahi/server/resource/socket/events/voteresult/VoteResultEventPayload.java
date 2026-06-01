package org.barahi.server.resource.socket.events.voteresult;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class VoteResultEventPayload implements EventPayload {
  @JsonProperty
  String targetPlayerId;
  @JsonProperty
  String answer;
  @JsonProperty
  boolean isApproved;

  public String getTargetPlayerId() {
    return targetPlayerId;
  }

  public void setTargetPlayerId(String targetPlayerId) {
    this.targetPlayerId = targetPlayerId;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public boolean isApproved() {
    return isApproved;
  }

  public void setApproved(boolean approved) {
    isApproved = approved;
  }
}
