package org.barahi.server.resource.socket.events.submitanswers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.SubmitAnswerPayloadEventJson;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;

public class SubmitAnswersEvent implements Event<SubmitAnswerPayloadEventJson> {
    @JsonProperty("payload")
    private SubmitAnswerPayloadEventJson eventPayloadJson;

    public SubmitAnswersEvent(SubmitAnswerPayloadEventJson eventPayloadJson) {
        this.eventPayloadJson = eventPayloadJson;
    }

    @Override
    @JsonProperty("type")
    public String getType() {
        return EventType.SUBMIT_ANSWERS.name();
    }

    @Override
    @JsonProperty("payload")
    public SubmitAnswerPayloadEventJson getPayload() {
        return eventPayloadJson;
    }

    @Override
    @JsonProperty("payload")
    public void setPayload(SubmitAnswerPayloadEventJson submitAnswersEventPayload) {
        this.eventPayloadJson = submitAnswersEventPayload;
    }
}
