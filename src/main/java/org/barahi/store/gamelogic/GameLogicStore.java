package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Map;

import static org.barahi.generated.Tables.*;

public class GameLogicStore {
  private final DSLContext db;

  @Inject
  public GameLogicStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }
  public void startGame(String roomId, List<PlayerId> playerIdList){
    // add players to cumulative_score table
    for (PlayerId p: playerIdList){
      db.insertInto(CUMULATIVE_SCORE)
        .set(CUMULATIVE_SCORE.PLAYER_ID, p.toString())
        .set(CUMULATIVE_SCORE.ROOM_ID, roomId)
        .set(CUMULATIVE_SCORE.SCORE, 0)
        .execute();
    }
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

  public int startNextRound(String roomId){
    int newRound = db.select(GAME_STATE.CURRENT_ROUND).from(GAME_STATE).where(GAME_STATE.ROOM_ID.eq(roomId)).execute();
    newRound++;
    db.update(GAME_STATE).set(GAME_STATE.CURRENT_ROUND, newRound++).where(GAME_STATE.ROOM_ID.eq(roomId)).execute();
    return newRound;
  }

  public Map<String, Integer> endGame(String roomId){
    return db.select(CUMULATIVE_SCORE.PLAYER_ID, CUMULATIVE_SCORE.SCORE)
      .from(CUMULATIVE_SCORE)
      .where(CUMULATIVE_SCORE.ROOM_ID.eq(roomId))
      .fetchMap(
        CUMULATIVE_SCORE.PLAYER_ID,
        CUMULATIVE_SCORE.SCORE
      );
  }
}
