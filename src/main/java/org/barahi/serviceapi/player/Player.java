package org.barahi.serviceapi.player;

import org.barahi.infra.TypedUUID;

import java.util.UUID;

public interface Player {
    PlayerId getId();

    String getUsername();

    class PlayerId extends TypedUUID<Player> {
        public PlayerId(UUID id) {
            super(id);
        }
    }
}
