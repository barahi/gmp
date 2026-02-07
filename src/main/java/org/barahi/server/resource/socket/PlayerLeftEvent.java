package org.barahi.server.resource.socket;

public class PlayerLeftEvent extends NoopEvent {
    @Override
    public String getType() {
        return EventType.PLAYER_LEFT.name();
    }
}
