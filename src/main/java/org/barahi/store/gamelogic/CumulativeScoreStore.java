package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.generated.tables.records.CumulativeScoreRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.jooq.Query;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.barahi.generated.Tables.CUMULATIVE_SCORE;

public class CumulativeScoreStore {
  private final DSLContext db;
  @Inject
  public CumulativeScoreStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public void initializeScores(RoomId roomId, List<PlayerId> playerIds){
    List<CumulativeScoreRecord> records = new ArrayList<>();
    playerIds.forEach(playerId -> {
      CumulativeScoreRecord record =
        new CumulativeScoreRecord(playerId.toString(), roomId.getId().toString(), 0);
      records.add(record);
    });
    db.batchInsert(records).execute();
  }

  public Map<PlayerId, Integer> getPlayerScores(RoomId roomId){
    Map<String, Integer> map =
      db.select(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE)
        .from(CUMULATIVE_SCORE)
        .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId.getId().toString()))
        .fetchMap(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE);

    return map.entrySet().stream().collect(Collectors.toMap(
      entry -> PlayerId.of(entry.getKey()),
      Map.Entry::getValue
    ));
  }

  public Map<PlayerId, Integer> updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> prevRoundScoreMap){
    List<Query> queries = new ArrayList<>();
    for (Map.Entry<PlayerId, Integer> entry : prevRoundScoreMap.entrySet()) {
      queries.add(
        db.update(CUMULATIVE_SCORE)
          .set(CUMULATIVE_SCORE.SCORE, CUMULATIVE_SCORE.SCORE.plus(entry.getValue()))
          .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId.getId().toString()))
          .and(CUMULATIVE_SCORE.PLAYER_ID.eq(entry.getKey().toString()))
      );
    }
    db.batch(queries).execute();
    return this.getPlayerScores(roomId);
  }
}
