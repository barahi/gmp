package org.barahi.service.player;

import jakarta.inject.Inject;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerImpl;
import org.barahi.serviceapi.player.PlayerService;
import org.barahi.store.PlayerStore;

import java.util.List;
import java.util.UUID;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerStore playerStore;

    @Inject
    public PlayerServiceImpl(PlayerStore playerStore) {
        this.playerStore = playerStore;
    }


    @Override
    public Player storePlayer(Player player) {
        Player playerWithId = new PlayerImpl(new PlayerId(UUID.randomUUID()), player.getUsername());
        playerStore.createPlayer(playerWithId);
        return playerWithId;
    }

    @Override
    public void removePlayer(List<PlayerId> ids) {
        playerStore.deletePlayer(ids);
    }

    @Override
    public Player getPlayer(PlayerId playerId) throws ObjectNotFoundException {
        return playerStore.getPlayer(playerId);
    }
}
