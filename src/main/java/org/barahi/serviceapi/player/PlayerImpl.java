package org.barahi.serviceapi.player;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class PlayerImpl implements Player {
    private final PlayerId id;
    private final String username;

    public PlayerImpl(PlayerId id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public PlayerId getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerImpl player = (PlayerImpl) o;
        return Objects.equals(getId(), player.getId()) && Objects.equals(getUsername(), player.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .toString();
    }
}
