package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.jooq.Query;

import java.util.*;
import java.util.stream.Collectors;

import static org.barahi.generated.Tables.*;

public class CumulativeScoreStore {
  private final DSLContext db;
  @Inject
  public CumulativeScoreStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public void initializeScores(RoomId roomId, List<PlayerId> playerIds) {
    playerIds.forEach(playerId -> {
        db.insertInto(CUMULATIVE_SCORE)
          .set(CUMULATIVE_SCORE.PLAYER_ID, playerId.getId().toString())
          .set(CUMULATIVE_SCORE.ROOM_ID, roomId.getId().toString())
          .set(CUMULATIVE_SCORE.SCORE, 0)
          .onDuplicateKeyIgnore().execute();
    });
  }

  public Map<String, Integer> getPlayerScores(RoomId roomId) {
    return db.select(PLAYER.USERNAME, CUMULATIVE_SCORE.SCORE, CUMULATIVE_SCORE.PLAYER_ID)
        .from(CUMULATIVE_SCORE)
        .join(PLAYER).on(CUMULATIVE_SCORE.PLAYER_ID.eq(PLAYER.ID))
        .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId.getId().toString()))
        .fetchMap(
          r -> r.get(PLAYER.USERNAME),
          r -> r.get(CUMULATIVE_SCORE.SCORE) != null ? r.get(CUMULATIVE_SCORE.SCORE) : 0
        );

  }


  public void updatePlayerScores(RoomId roomId, Map<PlayerId, Integer> prevRoundScoreMap) {
    for (Map.Entry<PlayerId, Integer> entry : prevRoundScoreMap.entrySet()) {
        db.update(CUMULATIVE_SCORE)
          .set(CUMULATIVE_SCORE.SCORE, CUMULATIVE_SCORE.SCORE.plus(entry.getValue()))
          .where(CUMULATIVE_SCORE.PLAYER_ID.eq(entry.getKey().getId().toString()))
          .and(CUMULATIVE_SCORE.ROOM_ID.eq(roomId.getId().toString())).execute();
    }
  }

}
