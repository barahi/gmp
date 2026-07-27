package org.barahi.store;

import jakarta.inject.Inject;

import org.barahi.generated.tables.records.RoomPlayerRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.service.room.RoomDto;
import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.Room.RoomId;
import org.barahi.serviceapi.room.RoomImpl;
import org.jooq.DSLContext;
import org.barahi.generated.tables.records.RoomRecord;
import org.jooq.Record1;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.barahi.generated.Tables.*;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

public class RoomStore {
    private final DSLContext db;

    @Inject
    public RoomStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public boolean isPlayerInRoom(RoomId roomId, PlayerId playerId){
         return db.fetchExists(db.selectFrom(ROOM_PLAYER)
           .where(ROOM_PLAYER.ROOM_ID.eq(roomId.getId().toString()))
           .and(ROOM_PLAYER.PLAYER_ID.eq(playerId.getId().toString())));
    }

    public RoomDto getRoomSettings(RoomId roomId){
        return db.select(ROOM.ID,
          ROOM.HOST_PLAYER_ID,
          GAME_SETTINGS.MAX_PLAYERS,
          GAME_SETTINGS.ROUND_DURATION,
          GAME_SETTINGS.NUMBER_OF_ROUNDS,
          GAME_SETTINGS.PASSWORD,
          multiset(
            select(CATEGORIES.CATEGORY)
              .from(CATEGORIES)
              .where(CATEGORIES.GAME_SETTINGS_ID.eq(GAME_SETTINGS.ID))
          ).convertFrom(record -> record.map(Record1::value1)),
          multiset(
            select(EXCLUDED_LETTER.LETTER)
              .from(EXCLUDED_LETTER)
              .where(EXCLUDED_LETTER.GAME_SETTINGS_ID.eq(GAME_SETTINGS.ID))
          ).convertFrom(record -> record.map(Record1::value1))
        ).from(ROOM)
          .join(GAME_SETTINGS).on(ROOM.ID.eq(GAME_SETTINGS.ROOM_ID))
          .where(ROOM.ID.eq(roomId.getId().toString()))
          .fetchOne(
            record -> new RoomDto(
              RoomId.of(record.get(ROOM.ID)),
              PlayerId.of(record.get(ROOM.HOST_PLAYER_ID)),
              record.get(GAME_SETTINGS.MAX_PLAYERS),
              record.get(GAME_SETTINGS.ROUND_DURATION),
              record.get(GAME_SETTINGS.NUMBER_OF_ROUNDS),
              record.get(GAME_SETTINGS.PASSWORD),
              record.value7(),
              record.value8()
            ));
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
                .onDuplicateKeyIgnore()
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

    public void deleteRoom(RoomId roomId) {
        db.deleteFrom(ROOM)
            .where(ROOM.ID.eq(roomId.getId().toString()))
            .execute();
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
