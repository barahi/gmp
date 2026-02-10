package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.generated.tables.records.PlayerAnswerRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.infra.TypedUUID;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;

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


  public Map<String, Map<PlayerId, String>> getAnswersForRound(List<PlayerId> playerIds, int roundNumber){
    return db.select(PLAYER_ANSWER.CATEGORY_ID, PLAYER_ANSWER.PLAYER_ID, PLAYER_ANSWER.ANSWER)
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.ROUND.eq(roundNumber))
      .and(PLAYER_ANSWER.PLAYER_ID.in(
        playerIds.stream().map(id -> id.getId().toString()).collect(Collectors.toList())))
      .fetch()
      .stream()
      .collect(Collectors.groupingBy(
        r -> r.get(PLAYER_ANSWER.CATEGORY_ID),
        Collectors.toMap(
          r -> PlayerId.of(r.get(PLAYER_ANSWER.PLAYER_ID)),
          r -> r.get(PLAYER_ANSWER.ANSWER)
        )
      ));
  }


  public void storeAnswers(String gameSettingsId, CategoryId categoryId, int round, Map<PlayerId, String> playerAnswers) {
    List<PlayerAnswerRecord> records = playerAnswers.entrySet().stream().map(
      pa ->
        new PlayerAnswerRecord(pa.getKey().getId().toString(), categoryId.toString(), gameSettingsId, round, pa.getValue(), 100)
    ).toList();
    db.batchInsert(records).execute();
  }

  public void updateScoreForAnswer(PlayerId playerId, CategoryId categoryId, int roundNum, int newScore){
    db.update(PLAYER_ANSWER)
      .set(PLAYER_ANSWER.SCORE, newScore)
      .where(PLAYER_ANSWER.PLAYER_ID.eq(playerId.getId().toString()))
      .and(PLAYER_ANSWER.CATEGORY_ID.eq(categoryId.getId().toString()))
      .and(PLAYER_ANSWER.ROUND.eq(roundNum))
      .execute();
  }
}
