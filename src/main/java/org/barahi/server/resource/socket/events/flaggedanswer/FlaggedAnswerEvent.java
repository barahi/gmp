package org.barahi.server.resource.socket.events.flaggedanswer;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.FlaggedAnswerPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class FlaggedAnswerEvent implements Event<FlaggedAnswerPayloadJson> {
  @JsonProperty("payload")
  private FlaggedAnswerPayloadJson flaggedAnswerPayloadJson;

  public FlaggedAnswerEvent(){}
  public FlaggedAnswerEvent(FlaggedAnswerPayloadJson flaggedAnswerPayloadJson) {
    this.flaggedAnswerPayloadJson = flaggedAnswerPayloadJson;
  }

  @Override
  @JsonProperty("type")
  public String getType() {
    return EventType.FLAGGED_ANSWER.name();
  }

  @Override
  @JsonProperty("payload")
  public FlaggedAnswerPayloadJson getPayload(){
    return flaggedAnswerPayloadJson;
  }

  @Override
  public void setPayload(FlaggedAnswerPayloadJson flaggedAnswerPayloadJson){
    this.flaggedAnswerPayloadJson = flaggedAnswerPayloadJson;
  }
}
