package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.SubmitVoteEventPayloadJson;

public class SubmitVoteEvent implements Event<SubmitVoteEventPayloadJson>{
    @JsonProperty("payload")
    private SubmitVoteEventPayloadJson voteInvalidEventPayloadJson;

    public SubmitVoteEvent(){}

    public SubmitVoteEvent(SubmitVoteEventPayloadJson voteInvalidEventPayloadJson) {
        this.voteInvalidEventPayloadJson = voteInvalidEventPayloadJson;
    }

    @Override
    @JsonProperty("type")
    public String getType() {
        return EventType.SUBMIT_VOTE.name();
    }

    @Override
    @JsonProperty("payload")
    public SubmitVoteEventPayloadJson getPayload() {
        return voteInvalidEventPayloadJson;
    }
    @Override
    @JsonProperty("payload")
    public void setPayload(SubmitVoteEventPayloadJson voteInvalidEventPayloadJson) {
        this.voteInvalidEventPayloadJson = voteInvalidEventPayloadJson;
    }
}
