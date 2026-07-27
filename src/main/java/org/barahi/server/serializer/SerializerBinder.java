package org.barahi.server.serializer;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class SerializerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PlayerSerializer.class).to(PlayerSerializer.class);
        bind(RoomSerializer.class).to(RoomSerializer.class);
        bind(SubmitAnswerPayloadEventSerializer.class).to(SubmitAnswerPayloadEventSerializer.class);
        bind(RoundScoreEventPayloadSerializer.class).to(RoundScoreEventPayloadSerializer.class);
        bind(SubmitVoteEventPayloadSerializer.class).to(SubmitVoteEventPayloadSerializer.class);
        bind(BeginVotePayloadEventSerializer.class).to(BeginVotePayloadEventSerializer.class);
        bind(VoteResultsEventPayloadSerializer.class).to(VoteResultsEventPayloadSerializer.class);
        bind(PlayerScoresPayloadEventSerializer.class).to(PlayerScoresPayloadEventSerializer.class);
        bind(EndGamePayloadEventSerializer.class).to(EndGamePayloadEventSerializer.class);
    }
}
