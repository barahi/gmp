package org.barahi.store;

import jakarta.inject.Inject;
import org.barahi.generated.tables.records.RoomPlayerRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.infra.exceptions.ObjectNotFoundException;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.player.PlayerImpl;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.barahi.generated.tables.records.PlayerRecord;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.barahi.generated.Tables.*;

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
    public RoomId getRoomIdForPlayer(PlayerId playerId){
        String roomId = db.selectFrom(ROOM_PLAYER)
          .where(ROOM_PLAYER.PLAYER_ID.eq(playerId.getId().toString()))
          .fetchOne(ROOM_PLAYER.ROOM_ID);
        return RoomId.of(roomId);
    }

    public List<Player> getPlayersInRoom(RoomId roomId){
        return db.select(PLAYER.fields())
          .from(PLAYER)
          .join(ROOM_PLAYER).on(PLAYER.ID.eq(ROOM_PLAYER.PLAYER_ID))
          .where(ROOM_PLAYER.ROOM_ID.eq(roomId.getId().toString()))
          .fetch( r -> {
              return new PlayerImpl(
                PlayerId.of(r.get(PLAYER.ID)),
                r.get(PLAYER.USERNAME)
              );
          });
    }

    public String getUsernameFromId(PlayerId playerId){
        return db.select(PLAYER.USERNAME)
          .from(PLAYER)
          .where(PLAYER.ID.eq(playerId.getId().toString()))
          .fetchOne(PLAYER.USERNAME);
    }

    public PlayerId getIdFromUsername(String username){
        String id = db.select(PLAYER.ID)
          .from(PLAYER)
          .where(PLAYER.USERNAME.eq(username))
          .fetchOne(PLAYER.ID);
        return id != null? PlayerId.of(id) : null;
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
