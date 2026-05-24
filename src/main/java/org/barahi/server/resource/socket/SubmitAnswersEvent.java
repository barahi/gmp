package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitAnswersEvent implements Event<SubmitAnswersEventPayload> {
    @JsonProperty("payload")
    private SubmitAnswersEventPayload submitAnswersEventPayload;
    public SubmitAnswersEvent() {}
    public SubmitAnswersEvent(SubmitAnswersEventPayload submitAnswersEventPayload) {
        this.submitAnswersEventPayload = submitAnswersEventPayload;
    }

    @Override
    @JsonProperty("type")
    public String getType() {
        return EventType.SUBMIT_ANSWERS.name();
    }

    @Override
    @JsonProperty("payload")
    public SubmitAnswersEventPayload getPayload() {
        return submitAnswersEventPayload;
    }

    @Override
    @JsonProperty("payload")
    public void setPayload(SubmitAnswersEventPayload submitAnswersEventPayload) {
        this.submitAnswersEventPayload = submitAnswersEventPayload;
    }
}
