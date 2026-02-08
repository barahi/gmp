package org.barahi.server.resource.socket;

public enum EventType {
    NOOP(false, false),
    PLAYER_JOINED(false, true),
    START_ROUND(true, true),
    SUBMIT_ANSWERS(true, false),
    VOTE_INVALID(true, false),
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
