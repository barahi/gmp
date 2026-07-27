package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.service.gamelogic.RoundPhase;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;

import static org.barahi.generated.Tables.*;

public class GameStateStore {
  private final DSLContext db;

  @Inject
  public GameStateStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }
  public Integer getCurrentRound(RoomId roomId){
    return db.select(GAME_STATE.CURRENT_ROUND)
      .from(GAME_STATE)
      .where(GAME_STATE.ROOM_ID.eq(roomId.getId().toString()))
      .fetchOne(GAME_STATE.CURRENT_ROUND);
  }

  public char getLetterForCurrentRound(RoomId roomId, int roundNumber){
    String letter = db.select(GAME_STATE.CURRENT_LETTER)
      .from(GAME_STATE)
      .where(GAME_STATE.ROOM_ID.eq(roomId.getId().toString()))
      .and(GAME_STATE.CURRENT_ROUND.eq(roundNumber))
      .fetchOne(GAME_STATE.CURRENT_LETTER);
    return letter != null? letter.charAt(0) : ' ';
  }

  public void changeGamePhaseAndRound(RoomId roomId, RoundPhase nextPhase, int roundNumber, char letterGenerated){
    db.insertInto(GAME_STATE)
      .set(GAME_STATE.ROOM_ID, roomId.getId().toString())
      .set(GAME_STATE.PHASE, nextPhase.name())
      .set(GAME_STATE.CURRENT_ROUND, roundNumber)
      .set(GAME_STATE.CURRENT_LETTER,String.valueOf(letterGenerated))
      .onDuplicateKeyUpdate()
      .setAllToExcluded()
      .execute();
  }

  public void changeGamePhase(RoomId roomId, RoundPhase phase){
    db.update(GAME_STATE)
      .set(GAME_STATE.PHASE, phase.name())
      .where(GAME_STATE.ROOM_ID.eq(roomId.toString()))
      .execute();
  }

}
