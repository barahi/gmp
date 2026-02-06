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

  public void initializeGameState(String roomId) {
    db.insertInto(GAME_STATE)
      .set(GAME_STATE.ROOM_ID, roomId)
      .set(GAME_STATE.CURRENT_ROUND, 1)
      .set(GAME_STATE.PHASE, "Submit")
      .execute();
  }
  public int getCurrentRound(String roomId){
    return db.select(GAME_STATE.CURRENT_ROUND)
      .from(GAME_STATE)
      .where(GAME_STATE.ROOM_ID.eq(roomId))
      .execute();
  }
  public void changeGamePhase(String roomId, String nextPhase){
    db.update(GAME_STATE).
      set(GAME_STATE.PHASE, nextPhase)
      .where(GAME_STATE.ROOM_ID.eq(roomId))
      .execute();
  }

  public void changeGameRound(String roomId, int newRound){
    db.update(GAME_STATE)
      .set(GAME_STATE.CURRENT_ROUND, newRound)
      .where(GAME_STATE.ROOM_ID.eq(roomId))
      .execute();
  }
}
