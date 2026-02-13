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

        public static PlayerId of(UUID id) {
            return new PlayerId(id);
        }

        public static PlayerId of(String id) throws IllegalArgumentException {
            return PlayerId.of(UUID.fromString(id));
        }

        public static PlayerId newId() {
          return new PlayerId(UUID.randomUUID());
        }
    }
}
