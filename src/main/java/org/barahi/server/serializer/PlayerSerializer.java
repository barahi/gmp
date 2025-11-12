package org.barahi.server.serializer;

import org.barahi.server.json.PlayerJson;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerImpl;

import java.util.UUID;

public class PlayerSerializer {
    public PlayerJson toJson(Player player) {
        PlayerJson json = new PlayerJson();
        json.setId(player.getId().getId().toString());
        json.setUsername(player.getUsername());
        return json;
    }

    public Player fromJson(PlayerJson json) {
        return new PlayerImpl(
                json.getId() == null ? null : new PlayerId(UUID.fromString(json.getId())),
                json.getUsername()
        );
    }
}
