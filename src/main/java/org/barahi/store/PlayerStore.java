package org.barahi.store;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerImpl;
import org.jooq.DSLContext;
import org.barahi.generated.tables.records.PlayerRecord;

import java.util.UUID;

import static org.barahi.generated.Tables.PLAYER;

public class PlayerStore {
    private final DSLContext db;

    @Inject
    public PlayerStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public Player getPlayer(PlayerId id) throws IllegalAccessException {
        PlayerRecord record = db.selectFrom(PLAYER)
                .where(PLAYER.ID.eq(id.getId().toString()))
                .fetchOne();
        if (record == null) {
            throw new IllegalAccessException(String.format("Tried to access non existing player with id: %s", id));
        }
        return fromRecord(record);
    }

    public void createPlayer(Player player) {
        PlayerRecord record = toRecord(player);
        db.insertInto(PLAYER)
          .set(record)
          .execute();
    }

    public void deletePlayer(PlayerId id) {
        db.delete(PLAYER)
           .where(PLAYER.ID.eq(id.getId().toString()))
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
