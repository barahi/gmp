package org.barahi.server.resource.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.infra.Functional;
import org.barahi.server.json.PlayerJson;
import org.barahi.server.serializer.PlayerSerializer;
import org.barahi.serviceapi.player.Player;

import java.util.List;

public class PlayerJoinedEventPayload implements EventPayload {
    private static final PlayerSerializer PLAYER_SERIALIZER = new PlayerSerializer();
    private List<Player> players;

    @JsonProperty("players")
    public List<PlayerJson> getPlayers() {
        return Functional.map(players, PLAYER_SERIALIZER::toJson);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
