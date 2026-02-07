package org.barahi.server.resource.socket;

public class SubmitAnswersEvent extends NoopEvent {
    @Override
    public String getType() {
        return EventType.SUBMIT_ANSWERS.name();
    }
}
