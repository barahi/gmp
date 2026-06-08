package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.server.resource.socket.EventPayload;

public class JoinRoomJson implements EventPayload {
    @JsonProperty
    private String playerId;

    @JsonProperty
    private String password;

    public JoinRoomJson setPlayerId(String playerId) {
        this.playerId = playerId;
        return this;
    }
    
    public String getPlayerId() {
        return playerId;
    }
    
    public JoinRoomJson setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public String getPassword() {
        return password;
    }
}
