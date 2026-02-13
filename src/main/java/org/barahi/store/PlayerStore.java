package org.barahi.store;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerImpl;
import org.jooq.DSLContext;
import org.barahi.generated.tables.records.PlayerRecord;

import java.util.List;
import java.util.UUID;

import static org.barahi.generated.Tables.PLAYER;

public class PlayerStore {
    private final DSLContext db;

    @Inject
    public PlayerStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public Player getPlayer(PlayerId id) throws ObjectNotFoundException {
        PlayerRecord record = db.selectFrom(PLAYER)
                .where(PLAYER.ID.eq(id.getId().toString()))
                .fetchOne();
        if (record == null) {
            throw new ObjectNotFoundException(Player.class, id);
        }
        return fromRecord(record);
    }

    public void createPlayer(Player player) {
        PlayerRecord record = toRecord(player);
        db.insertInto(PLAYER)
          .set(record)
          .execute();
    }

public void deletePlayer(List<Player.PlayerId> ids) {

    if (ids == null || ids.isEmpty()) {
        return;
    }

    List<String> rawIds = ids.stream()
            .map(id -> id.getId().toString())
            .toList();

    db.deleteFrom(PLAYER)
            .where(PLAYER.ID.in(rawIds))
            .execute();
}


    private PlayerRecord toRecord(Player player) {
        PlayerRecord record = new PlayerRecord();
        record.setId(player.getId().getId().toString());
        record.setUsername(player.getUsername());
        return record;
    }

    private Player fromRecord(PlayerRecord record) {
        return new PlayerImpl(new PlayerId(UUID.fromString(record.getId())), record.getUsername());
    }

}
