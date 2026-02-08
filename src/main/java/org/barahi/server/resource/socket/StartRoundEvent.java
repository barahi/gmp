package org.barahi.server.resource.socket;

public class StartRoundEvent implements Event<StartRoundEventPayload> {
    @Override
    public String getType() {
        return EventType.START_ROUND.name();
    }

    @Override
    public StartRoundEventPayload getPayload() {
        return new StartRoundEventPayload();
    }

    @Override
    public void setPayload(StartRoundEventPayload payload) {}
}
