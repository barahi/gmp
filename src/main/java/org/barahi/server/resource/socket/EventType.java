package org.barahi.server.resource.socket;

public enum EventType {
    NOOP(false, false),
    PLAYER_JOINED(false, true),
    START_ROUND(true, true),
    SUBMIT_ANSWERS(true, false),
    ROUND_SCORES(false, true),
    BEGIN_VOTE_PHASE(true, false),
    SUBMIT_VOTE(true, false),
    END_VOTE_PHASE(true, false),
    VOTE_RESULTS(false, true),
    END_ROUND(true, false),
    ROUND_RESULTS(false, true),
    END_GAME(false, true),
    PLAYER_LEFT(false, true);

    private final boolean isIncoming;
    private final boolean isOutgoing;

    EventType(boolean isIncoming, boolean isOutgoing) {
        this.isIncoming = isIncoming;
        this.isOutgoing = isOutgoing;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public boolean isOutgoing() {
        return isOutgoing;
    }
}
