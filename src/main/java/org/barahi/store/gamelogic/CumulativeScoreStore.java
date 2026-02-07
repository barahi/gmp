package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;

import java.util.Map;
import java.util.stream.Collectors;

import static org.barahi.generated.Tables.CUMULATIVE_SCORE;

public class CumulativeScoreStore {
  private final DSLContext db;
  @Inject
  public CumulativeScoreStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public Map<PlayerId, Integer> getPlayerScores(RoomId roomId){
    Map<String, Integer> map =
      db.select(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE)
        .from(CUMULATIVE_SCORE)
        .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId.toString()))
        .fetchMap(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE);

    return map.entrySet().stream().collect(Collectors.toMap(
      entry -> PlayerId.of(entry.getKey()),
      Map.Entry::getValue
    ));
  }

  public Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> prevRoundScoreMap){
    for (Map.Entry<PlayerId, Integer> entry : prevRoundScoreMap.entrySet()) {
      db.update(CUMULATIVE_SCORE)
        .set(CUMULATIVE_SCORE.SCORE, entry.getValue())
        .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId.toString()))
        .and(CUMULATIVE_SCORE.PLAYER_ID.eq(entry.getKey().toString()))
        .execute();
    }
    return this.getPlayerScores(roomId);
  }
}
