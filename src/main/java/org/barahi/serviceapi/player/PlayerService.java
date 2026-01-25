package org.barahi.serviceapi.player;

import org.barahi.serviceapi.player.Player.PlayerId;

public interface PlayerService {
    Player getPlayer(PlayerId id) throws IllegalAccessException;

    Player storePlayer(Player unsavedPlayer);

    void removePlayer(PlayerId id);
}
