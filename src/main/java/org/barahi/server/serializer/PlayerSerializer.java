package org.barahi.server.serializer;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.barahi.server.json.PlayerJson;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerImpl;

import java.io.IOException;
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
    public static class PlayerIdKeyDeserializer extends KeyDeserializer {
        @Override
        public Object deserializeKey(String key, DeserializationContext context) throws IOException {
            try {
                return PlayerId.of(key);
            } catch (IllegalArgumentException e){
                throw new IOException("Failed to parse PlayerId from id string: " + key + e);
            }
        }
    }
}