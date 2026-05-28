package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhaseEvent;
import org.barahi.server.resource.socket.events.noop.NoopEvent;
import org.barahi.server.resource.socket.events.playerjoined.PlayerJoinedEvent;
import org.barahi.server.resource.socket.events.playerleft.PlayerLeftEvent;
import org.barahi.server.resource.socket.events.roundscore.RoundScoreEvent;
import org.barahi.server.resource.socket.events.startround.StartRoundEvent;
import org.barahi.server.resource.socket.events.submitanswers.SubmitAnswersEvent;
import org.barahi.server.resource.socket.events.submitvote.SubmitVoteEvent;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayerJoinedEvent.class, name = "PLAYER_JOINED"),
        @JsonSubTypes.Type(value = NoopEvent.class, name = "NOOP"),
        @JsonSubTypes.Type(value = StartRoundEvent.class, name = "START_ROUND"),
        @JsonSubTypes.Type(value = SubmitAnswersEvent.class, name = "SUBMIT_ANSWERS"),
        @JsonSubTypes.Type(value = RoundScoreEvent.class, name = "ROUND_SCORES"),
        @JsonSubTypes.Type(value = BeginVotePhaseEvent.class, name = "BEGIN_VOTE_PHASE"),
        @JsonSubTypes.Type(value = SubmitVoteEvent.class, name = "SUBMIT_VOTE"),
        @JsonSubTypes.Type(value = PlayerLeftEvent.class, name = "PLAYER_LEFT")
})
public interface Event<T extends EventPayload> {
    @JsonProperty("type")
    String getType();

    @JsonProperty("payload")
    T getPayload();

    void setPayload(T payload);
}
