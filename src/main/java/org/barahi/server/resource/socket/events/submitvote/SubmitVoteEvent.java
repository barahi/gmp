package org.barahi.server.resource.socket.events.submitvote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.SubmitVotePayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class SubmitVoteEvent implements Event<SubmitVotePayloadJson> {
    @JsonProperty("payload")
    private SubmitVotePayloadJson voteInvalidEventPayloadJson;

    public SubmitVoteEvent(){}

    public SubmitVoteEvent(SubmitVotePayloadJson voteInvalidEventPayloadJson) {
        this.voteInvalidEventPayloadJson = voteInvalidEventPayloadJson;
    }

    @Override
    @JsonProperty("type")
    public String getType() {
        return EventType.SUBMIT_VOTE.name();
    }

    @Override
    @JsonProperty("payload")
    public SubmitVotePayloadJson getPayload() {
        return voteInvalidEventPayloadJson;
    }
    @Override
    @JsonProperty("payload")
    public void setPayload(SubmitVotePayloadJson voteInvalidEventPayloadJson) {
        this.voteInvalidEventPayloadJson = voteInvalidEventPayloadJson;
    }
}
