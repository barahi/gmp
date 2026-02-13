package org.barahi.store;

import jakarta.inject.Inject;
import javassist.tools.rmi.ObjectNotFoundException;

import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomImpl;
import org.jooq.DSLContext;
import org.barahi.generated.tables.records.RoomRecord;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.barahi.generated.Tables.*;

public class RoomStore {
    private final DSLContext db;

    @Inject
    public RoomStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public void createRoom(Room room) {
        RoomRecord record = toRecord(room);
        db.insertInto(ROOM)
                .set(record)
                .execute();
    }

    public void addPlayerToRoom(Room.RoomId roomId, Player.PlayerId playerId) {
        db.insertInto(ROOM_PLAYER)
                .set(ROOM_PLAYER.ROOM_ID, roomId.getId().toString())
                .set(ROOM_PLAYER.PLAYER_ID, playerId.getId().toString())
                .execute();
    }

    public List<PlayerId> getPlayerIdsInRoom(RoomId roomId) {
        return db.select(ROOM_PLAYER.PLAYER_ID)
          .from(ROOM_PLAYER)
          .where(ROOM_PLAYER.ROOM_ID.eq(roomId.getId().toString()))
          .fetch(ROOM_PLAYER.PLAYER_ID)
          .stream().map(PlayerId::of)
          .toList();
    }

    // getRoomWithID gets room with room's id
    public Room getRoomWithId(RoomId id) throws IllegalAccessException {
        RoomRecord record = db.selectFrom(ROOM)
            .where(ROOM.ID.eq(id.getId().toString()))
            .fetchOne();
        if (record == null) {
            throw new IllegalAccessException(String.format("Tried to access nonexistent room with id: %s", id));
        }
        return fromRecord(record);
    }

    public Integer getCurrentPlayersInRoomCount(RoomId roomId) {
            return db.selectCount()
            .from(ROOM_PLAYER)
            .where(ROOM_PLAYER.ROOM_ID.eq(roomId.getId().toString()))
            .fetchOne(0, Integer.class);
    }

    private RoomRecord toRecord(Room room) {
        RoomRecord record = new RoomRecord();
        record.setId(room.getId().getId().toString());
        record.setHostPlayerId(room.getHostPlayerId().getId().toString());
        record.setIsGameStarted(room.isGameStarted());
        record.setCreatedAt(LocalDateTime.ofInstant(room.getCreatedAt(), ZoneOffset.UTC));
        return record;
    }

    private Room fromRecord(RoomRecord record) {
        return new RoomImpl(
                new Room.RoomId(UUID.fromString(record.getId())),
                new Player.PlayerId(UUID.fromString(record.getHostPlayerId())),
                record.getIsGameStarted(),
                record.getCreatedAt().toInstant(ZoneOffset.UTC)
        );
    }

}
