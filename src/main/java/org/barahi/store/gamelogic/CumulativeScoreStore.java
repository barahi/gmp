package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;

import java.util.Map;

import static org.barahi.generated.Tables.CUMULATIVE_SCORE;

public class CumulativeScoreStore {
  private final DSLContext db;
  @Inject
  public CumulativeScoreStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public Map<String, Integer> getPlayerScores(String roomId){
    return db.select(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE)
      .from(CUMULATIVE_SCORE)
      .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId))
      .fetchMap(
        CUMULATIVE_SCORE.PLAYER_ID,
        CUMULATIVE_SCORE.SCORE
      );
  }

  public Map<String, Integer> updatePlayerScores(String roomId, Map<PlayerId, Integer> prevRoundScoreMap){
    for (Map.Entry<PlayerId, Integer> entry: prevRoundScoreMap.entrySet() ){
      db.update(CUMULATIVE_SCORE)
        .set(CUMULATIVE_SCORE.SCORE, entry.getValue())
        .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId))
        .and(CUMULATIVE_SCORE.PLAYER_ID.eq(entry.getKey().toString()))
        .execute();
    }
    return db.select(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE)
      .from(CUMULATIVE_SCORE)
      .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId))
      .fetchMap(
        CUMULATIVE_SCORE.PLAYER_ID,
        CUMULATIVE_SCORE.SCORE
      );
  }
}
