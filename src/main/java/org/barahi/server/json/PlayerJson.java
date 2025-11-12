package org.barahi.server.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerJson {
    @JsonProperty
    private String id;

    @JsonProperty
    private String username;

    public String getId() {
        return id;
    }

    public PlayerJson setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PlayerJson setUsername(String username) {
        this.username = username;
        return this;
    }
}
