package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.generated.tables.records.PlayerAnswerRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Map;

import static org.barahi.generated.Tables.*;

public class PlayerAnswerStore {
  private final DSLContext db;

  @Inject
  public PlayerAnswerStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public void initializePlayerAnswer(String roomId, List<PlayerId> playerIdList) {
    for (PlayerId p : playerIdList) {
      db.insertInto(CUMULATIVE_SCORE)
        .set(CUMULATIVE_SCORE.PLAYER_ID, p.toString())
        .set(CUMULATIVE_SCORE.ROOM_ID, roomId)
        .set(CUMULATIVE_SCORE.SCORE, 0)
        .execute();
    }
  }

  public void storeAnswers(Map<PlayerId, String> playerAnswers, String category, int roundNumber) {
    List<PlayerAnswerRecord> records = playerAnswers.entrySet().stream().map(
      pa -> new PlayerAnswerRecord(null, pa.getKey().toString(), pa.getValue(), category, 100, roundNumber)
    ).toList();
    db.batchInsert(records).execute();
  }

  public void updateScoreForAnswer(PlayerId playerId, String category, int newScore){
    db.update(PLAYER_ANSWER)
      .set(PLAYER_ANSWER.SCORE, newScore)
      .where(PLAYER_ANSWER.PLAYER_ID.eq(playerId.toString()))
      .and(PLAYER_ANSWER.CATEGORY.eq(category))
      .execute();
  }
}
