package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayerJoinedEvent.class),
        @JsonSubTypes.Type(value = NoopEvent.class),
        @JsonSubTypes.Type(value = StartRoundEvent.class),
        @JsonSubTypes.Type(value = SubmitAnswersEvent.class),
        @JsonSubTypes.Type(value = VoteInvalidEvent.class),
        @JsonSubTypes.Type(value = PlayerLeftEvent.class)
})
public interface Event<T extends EventPayload> {
    @JsonProperty("type")
    String getType();

    @JsonProperty("payload")
    T getPayload();

    void setPayload(T payload);
}
