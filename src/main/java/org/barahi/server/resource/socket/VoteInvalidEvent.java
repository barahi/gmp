package org.barahi.server.resource.socket;

public class VoteInvalidEvent extends NoopEvent {
    @Override
    public String getType() {
        return EventType.VOTE_INVALID.name();
    }
}
