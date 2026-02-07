package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinRoomJson {
    @JsonProperty
    private String id;

    @JsonProperty
    private String password;

    public JoinRoomJson setPlayerId(String id) {
        this.id = id;
        return this;
    }
    
    public String getPlayerId() {
        return id;
    }
    
    public JoinRoomJson setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public String getPassword() {
        return password;
    }
}
