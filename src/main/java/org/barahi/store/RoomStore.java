package org.barahi.store;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.barahi.generated.Tables.ROOM;
import org.barahi.generated.tables.records.RoomRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.room.Room;
import org.barahi.serviceapi.room.RoomImpl;
import org.jooq.DSLContext;

import jakarta.inject.Inject;

public class RoomStore {
    private final DSLContext db;

    @Inject
    public RoomStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public Room getRoom(Room.RoomId id) throws IllegalAccessException {
        RoomRecord record = db.selectFrom(ROOM)
                .where(ROOM.ID.eq(id.getId().toString()))
                .fetchOne();
        if (record == null) {
            throw new IllegalAccessException(String.format("Tried to access non existing room with id: %s", id));
        }
        return fromRecord(record);
    }

    public Room storeRoom(Room unsavedRoom) {
        RoomRecord record = toRecord(unsavedRoom);
        db.insertInto(ROOM)
          .set(record)
          .execute();
        return unsavedRoom;
    }

    public void removeRoom(Room.RoomId id) {
        db.delete(ROOM)
           .where(ROOM.ID.eq(id.getId().toString()))
           .execute();
    }

    private RoomRecord toRecord(Room room) {
        RoomRecord record = new RoomRecord();
        record.setId(room.getId().toString());
        record.setHostPlayerId(room.getHostPlayerId().toString());
        record.setPlayerIds(uuidsToStrings(room.getPlayerIds()));
        record.setMaxPlayers(room.getMaxPlayers());
        record.setRoundCount(room.getRoundCount());
        record.setLanguage(room.getLanguage());
        record.setStatus(room.getStatus().toString());
        record.setCurrentRound(room.getCurrentRound());
        record.setCreatedAt(instantToLocalDateTime(room.getCreatedAt()));
        record.setUpdatedAt(instantToLocalDateTime(room.getUpdatedAt()));
        return record;
    }

    private Room fromRecord(RoomRecord record) {
        return new RoomImpl(
                java.util.UUID.fromString(record.getId()),
                java.util.UUID.fromString(record.getHostPlayerId()),
                stringsToUuids(record.getPlayerIds()),
                record.getMaxPlayers(),
                record.getRoundCount(),
                record.getLanguage(),
                Room.RoomStatus.valueOf(record.getStatus()),
                record.getCurrentRound(),
                localDateTimeToInstant(record.getCreatedAt()),
                localDateTimeToInstant(record.getUpdatedAt())
        );
    }

    private String[] uuidsToStrings(java.util.UUID[] uuids) {
        if (uuids == null) {
            return null;
        }
        String[] strings = new String[uuids.length];
        for (int i = 0; i < uuids.length; i++) {
            strings[i] = uuids[i].toString();
        }
        return strings;
    }

    private java.util.UUID[] stringsToUuids(String[] strings) {
        if (strings == null) {
            return null;
        }
        java.util.UUID[] uuids = new java.util.UUID[strings.length];
        for (int i = 0; i < strings.length; i++) {
            uuids[i] = java.util.UUID.fromString(strings[i]);
        }
        return uuids;
    }

    private LocalDateTime instantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}
