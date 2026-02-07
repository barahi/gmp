package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.generated.tables.records.PlayerAnswerRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.barahi.generated.Tables.*;

public class PlayerAnswerStore {
  private final DSLContext db;

  @Inject
  public PlayerAnswerStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }


  public Map<String, Map<PlayerId, String>> getAnswersForRound(int roundNumber){
    return db.select(PLAYER_ANSWER.CATEGORY, PLAYER_ANSWER.PLAYER_ID, PLAYER_ANSWER.ANSWER)
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.ROUND.eq(roundNumber))
      .fetch()
      .stream()
      .collect(Collectors.groupingBy(
        r -> r.get(PLAYER_ANSWER.CATEGORY),
        Collectors.toMap(
          r -> PlayerId.of(r.get(PLAYER_ANSWER.PLAYER_ID)),
          r -> r.get(PLAYER_ANSWER.ANSWER)
        )
      ));
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
