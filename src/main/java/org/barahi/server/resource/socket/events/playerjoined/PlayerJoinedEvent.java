package org.barahi.server.resource.socket.events.playerjoined;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.Event;
import org.barahi.server.resource.socket.EventType;
import org.barahi.serviceapi.player.Player;

import java.util.List;

public class PlayerJoinedEvent implements Event<PlayerJoinedEventPayload> {
    private PlayerJoinedEventPayload payload;

    @Override
    @JsonProperty("type")
    public String getType() {
        return EventType.PLAYER_JOINED.name();
    }

    @Override
    @JsonProperty("payload")
    public PlayerJoinedEventPayload getPayload() {
        return payload;
    }

    @Override
    public void setPayload(PlayerJoinedEventPayload payload) {
        this.payload = payload;
    }

    public static PlayerJoinedEvent withListOfPlayers(List<Player> players) {
        PlayerJoinedEvent event = new PlayerJoinedEvent();
        PlayerJoinedEventPayload payload = new PlayerJoinedEventPayload();
        payload.setPlayers(players);
        event.setPayload(payload);
        return event;
    }
}
