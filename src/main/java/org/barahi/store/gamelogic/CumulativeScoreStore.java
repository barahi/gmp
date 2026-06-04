package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.jooq.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.barahi.generated.Tables.CUMULATIVE_SCORE;

public class CumulativeScoreStore {
  private final DSLContext db;
  @Inject
  public CumulativeScoreStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public void initializeScores(RoomId roomId, List<PlayerId> playerIds) {
    List<Query> queries = new ArrayList<>();
    playerIds.forEach(playerId -> {
      queries.add(
        db.insertInto(CUMULATIVE_SCORE)
          .set(CUMULATIVE_SCORE.PLAYER_ID, playerId.getId().toString())
          .set(CUMULATIVE_SCORE.ROOM_ID, roomId.getId().toString())
          .set(CUMULATIVE_SCORE.SCORE, 0)
          .onDuplicateKeyUpdate()
          .set(CUMULATIVE_SCORE.ROOM_ID, roomId.getId().toString())
          .set(CUMULATIVE_SCORE.SCORE, 0)
      );
    });

    db.batch(queries).execute();
  }

  public Map<PlayerId, Integer> getPlayerScores(RoomId roomId){
    Map<PlayerId, Integer> playerScoreMap = new HashMap<>();

    db.select(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE)
      .from(CUMULATIVE_SCORE)
      .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId.getId().toString()))
      .fetch()
        .forEach(r -> {
          String playerId = r.get(CUMULATIVE_SCORE.PLAYER_ID);
          Integer score = r.get(CUMULATIVE_SCORE.SCORE);
          playerScoreMap.put(PlayerId.of(playerId), score != null ? score : 0);
        });
    return playerScoreMap;
  }

  public Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> prevRoundScoreMap) {
    List<Query> queries = new ArrayList<>();
    for (Map.Entry<PlayerId, Integer> entry : prevRoundScoreMap.entrySet()) {
      queries.add(
        db.update(CUMULATIVE_SCORE)
          .set(CUMULATIVE_SCORE.SCORE, CUMULATIVE_SCORE.SCORE.plus(entry.getValue()))
          .where(CUMULATIVE_SCORE.PLAYER_ID.eq(entry.getKey().getId().toString())) // Filter strictly by player PK
      );
    }
    db.batch(queries).execute();
    return prevRoundScoreMap;
  }
}
