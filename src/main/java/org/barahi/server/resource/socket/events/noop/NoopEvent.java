package org.barahi.server.resource.socket.events.noop;

import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventPayload;
import org.barahi.server.resource.socket.EventType;

public class NoopEvent implements Event<NoopEvent.NoopEventPayload> {
    @Override
    public String getType() {
        return EventType.NOOP.name();
    }

    @Override
    public NoopEventPayload getPayload() {
        return new NoopEventPayload();
    }

    @Override
    public void setPayload(NoopEventPayload payload) {}

    public static class NoopEventPayload implements EventPayload {}
}
