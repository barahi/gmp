package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayerJoinedEvent.class, name = "PLAYER_JOINED"),
        @JsonSubTypes.Type(value = NoopEvent.class, name = "NOOP"),
        @JsonSubTypes.Type(value = StartRoundEvent.class, name = "START_ROUND"),
        @JsonSubTypes.Type(value = SubmitAnswersEvent.class, name = "SUBMIT_ANSWERS"),
        @JsonSubTypes.Type(value = VoteInvalidEvent.class, name = "VOTE_INVALID"),
        @JsonSubTypes.Type(value = PlayerLeftEvent.class, name = "PLAYER_LEFT")
})
public interface Event<T extends EventPayload> {
    @JsonProperty("type")
    String getType();

    @JsonProperty("payload")
    T getPayload();

    void setPayload(T payload);
}
