package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.json.SubmitAnswerPayloadEventJson;

public class SubmitAnswersEvent implements Event<SubmitAnswerPayloadEventJson> {
    @JsonProperty("payload")
    private SubmitAnswerPayloadEventJson eventPayloadJson;
    public SubmitAnswersEvent() {}

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
