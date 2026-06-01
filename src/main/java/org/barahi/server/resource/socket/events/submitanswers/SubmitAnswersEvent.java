package org.barahi.server.resource.socket.events.submitanswers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.SubmitAnswerPayloadJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class SubmitAnswersEvent implements Event<SubmitAnswerPayloadJson> {
    @JsonProperty("payload")
    private SubmitAnswerPayloadJson eventPayloadJson;

    public SubmitAnswersEvent(SubmitAnswerPayloadJson eventPayloadJson) {
        this.eventPayloadJson = eventPayloadJson;
    }

    @Override
    @JsonProperty("type")
    public String getType() {
        return EventType.SUBMIT_ANSWERS.name();
    }

    @Override
    @JsonProperty("payload")
    public SubmitAnswerPayloadJson getPayload() {
        return eventPayloadJson;
    }

    @Override
    @JsonProperty("payload")
    public void setPayload(SubmitAnswerPayloadJson submitAnswersEventPayload) {
        this.eventPayloadJson = submitAnswersEventPayload;
    }
}
