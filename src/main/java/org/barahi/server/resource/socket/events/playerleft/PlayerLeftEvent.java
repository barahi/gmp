package org.barahi.server.resource.socket.events.playerleft;

import org.barahi.server.resource.socket.EventType;
import org.barahi.server.resource.socket.events.noop.NoopEvent;

public class PlayerLeftEvent extends NoopEvent {
    @Override
    public String getType() {
        return EventType.PLAYER_LEFT.name();
    }
}
