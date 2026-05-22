package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartRoundEvent implements Event<StartRoundEventPayload> {
    @JsonProperty("payload")
    private StartRoundEventPayload startRoundEventPayload;

    public StartRoundEvent() {}
    public StartRoundEvent(StartRoundEventPayload startRoundEventPayload) {
        this.startRoundEventPayload = startRoundEventPayload;
    }
    @Override
    public String getType() {
        return EventType.START_ROUND.name();
    }

    @Override
    @JsonProperty("payload")
    public StartRoundEventPayload getPayload() {
        return startRoundEventPayload;
    }

    @Override
    @JsonProperty("payload")
    public void setPayload(StartRoundEventPayload payload) {
        this.startRoundEventPayload = payload;
    }
}
