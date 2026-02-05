package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinRoomJson {
    @JsonProperty
    private String id;

    @JsonProperty
    private String password;

    public JoinRoomJson setRoomId(String id) {
        this.id = id;
        return this;
    }
    
    public String getRoomId() {
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
