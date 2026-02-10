package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.service.gamelogic.GameLogicServiceImpl;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;

import static org.barahi.generated.Tables.*;

public class GameStateStore {
  private final DSLContext db;

  @Inject
  public GameStateStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public int getCurrentRound(RoomId roomId){
    return db.select(GAME_STATE.CURRENT_ROUND)
      .from(GAME_STATE)
      .where(GAME_STATE.ROOM_ID.eq(roomId.toString()))
      .execute();
  }
  public void changeGamePhaseAndRound(RoomId roomId, GameLogicServiceImpl.RoundPhase nextPhase, int roundNumber){
    db.update(GAME_STATE)
      .set(GAME_STATE.PHASE, nextPhase.name())
      .set(GAME_STATE.CURRENT_ROUND, roundNumber)
      .where(GAME_STATE.ROOM_ID.eq(roomId.toString()))
      .execute();
  }

  public void changeGamePhase(RoomId roomId, GameLogicServiceImpl.RoundPhase phase){
    db.update(GAME_STATE)
      .set(GAME_STATE.PHASE, phase.name())
      .where(GAME_STATE.ROOM_ID.eq(roomId.toString()))
      .execute();
  }
}
