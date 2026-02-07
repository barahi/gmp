package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.jooq.DSLContext;

import static org.barahi.generated.Tables.*;

public class GameStateStore {
  private final DSLContext db;

  @Inject
  public GameStateStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public int getCurrentRound(String roomId){
    return db.select(GAME_STATE.CURRENT_ROUND)
      .from(GAME_STATE)
      .where(GAME_STATE.ROOM_ID.eq(roomId))
      .execute();
  }
  public void changeGamePhaseAndRound(String roomId, String nextPhase, int roundNumber){
    db.update(GAME_STATE)
      .set(GAME_STATE.PHASE, nextPhase)
      .set(GAME_STATE.CURRENT_ROUND, roundNumber)
      .where(GAME_STATE.ROOM_ID.eq(roomId))
      .execute();
  }

  public void changeGamePhase(String roomId, String phase){
    db.update(GAME_STATE)
      .set(GAME_STATE.PHASE, phase)
      .where(GAME_STATE.ROOM_ID.eq(roomId))
      .execute();
  }
}
