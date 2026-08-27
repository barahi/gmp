package org.barahi.serviceapi.player;

import java.util.List;

import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.infra.exceptions.UsernameAlreadyTakenException;
import org.barahi.serviceapi.player.Player.PlayerId;

public interface PlayerService {
    Player getPlayer(PlayerId id) throws ObjectNotFoundException;
    Player storePlayer(Player unsavedPlayer);
    String getUsernameFromId(PlayerId playerId);
    PlayerId getIdFromUsername(String username);
    void removePlayer(List<PlayerId> ids);
}
