package org.barahi.store;

import static org.barahi.generated.Tables.LOBBY;
import org.barahi.generated.tables.records.LobbyRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.lobby.Lobby;
import org.barahi.serviceapi.lobby.Lobby.LobbyId;
import org.barahi.serviceapi.lobby.LobbyImpl;
import org.jooq.DSLContext;

import jakarta.inject.Inject;

public class LobbyStore {
    private final DSLContext db;

    @Inject
    public LobbyStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public Lobby getLobby(LobbyId id) throws IllegalAccessException {
        LobbyRecord record = db.selectFrom(LOBBY)
                .where(LOBBY.ID.eq(id.getId()))
                .fetchOne();
        if (record == null) {
            throw new IllegalAccessException(String.format("Tried to access non existing lobby with id: %s", id));
        }
        return fromRecord(record);
    }

    public Lobby storeLobby(Lobby unsavedLobby) {
        LobbyRecord record = toRecord(unsavedLobby);
        db.insertInto(LOBBY)
          .set(record)
          .execute();
        return unsavedLobby;
    }

    public void removeLobby(LobbyId id) {
        db.delete(LOBBY)
           .where(LOBBY.ID.eq(id.getId()))
           .execute();
    }

    private LobbyRecord toRecord(Lobby lobby) {
        LobbyRecord record = new LobbyRecord();
        record.setId(lobby.getId());
        record.setGameSettingsId(lobby.getGameSettingsId());
        record.setPlayerIds(lobby.getPlayerIds());
        record.setIsGameStarted(lobby.isGameStarted());
        return record;
    }

    private Lobby fromRecord(LobbyRecord record) {
        return new LobbyImpl(
                record.getId(),
                record.getGameSettingsId(),
                record.getPlayerIds(),
                record.getIsGameStarted()
        );
    }
}
