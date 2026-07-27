package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.generated.tables.records.PlayerAnswerRecord;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.gameSettings.GameSettings.GameSettingsId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.barahi.generated.Tables.*;

public class PlayerAnswerStore {
  private final DSLContext db;

  @Inject
  public PlayerAnswerStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public String findPlayerAnswerId(GameSettingsId gameSettingsId, int roundNumber, CategoryId categoryId, PlayerId playerId, String answer){
    return db.select(PLAYER_ANSWER.ID)
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.GAME_SETTINGS_ID.eq(gameSettingsId.getId().toString()))
      .and(PLAYER_ANSWER.PLAYER_ID.eq(playerId.getId().toString()))
      .and(PLAYER_ANSWER.ROUND.eq(roundNumber))
      .and(PLAYER_ANSWER.CATEGORY_ID.eq(categoryId.getId().toString()))
      .and(PLAYER_ANSWER.ANSWER.eq(answer))
      .fetchOne(PLAYER_ANSWER.ID);
  }

  public Integer getPlayerAnswerScore(String playerAnswerId){
    return db.select(PLAYER_ANSWER.SCORE)
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.ID.eq(playerAnswerId))
      .fetchOne(PLAYER_ANSWER.SCORE);
  }


  public Map<String, Map<PlayerId, String>> getAnswersForRound(List<PlayerId> playerIds, int roundNumber){
    return db.select(CATEGORIES.CATEGORY, PLAYER_ANSWER.PLAYER_ID, PLAYER_ANSWER.ANSWER)
      .from(PLAYER_ANSWER)
      .join(CATEGORIES).on(PLAYER_ANSWER.CATEGORY_ID.eq(CATEGORIES.ID))
      .where(PLAYER_ANSWER.ROUND.eq(roundNumber))
      .and(PLAYER_ANSWER.SCORE.ne(-1))
      .and(PLAYER_ANSWER.PLAYER_ID.in(
        playerIds.stream().map(id -> id.getId().toString()).collect(Collectors.toList())))
      .fetch()
      .stream()
      .collect(Collectors.groupingBy(
        r -> r.get(CATEGORIES.CATEGORY),
        Collectors.toMap(
          r -> PlayerId.of(r.get(PLAYER_ANSWER.PLAYER_ID)),
          r -> r.get(PLAYER_ANSWER.ANSWER)
        )
      ));
  }

  public Map<PlayerId, Integer> getScoresForRound(GameSettingsId gameSettingsId, int roundNumber){
    return db.select(PLAYER_ANSWER.PLAYER_ID, DSL.sum(PLAYER_ANSWER.SCORE).cast(Integer.class))
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.GAME_SETTINGS_ID.eq(gameSettingsId.getId().toString()))
      .and(PLAYER_ANSWER.ROUND.eq(roundNumber))
      .groupBy(PLAYER_ANSWER.PLAYER_ID)
      .fetchMap(
        r -> PlayerId.of(r.get(PLAYER_ANSWER.PLAYER_ID)),
        r -> r.get(1, Integer.class)
      );
  }

  public Integer getPlayerAnswersForRound(int roundNumber, GameSettingsId gameSettingsId){
    return db.selectCount()
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.ROUND.eq(roundNumber))
      .and(PLAYER_ANSWER.GAME_SETTINGS_ID.eq(gameSettingsId.getId().toString())).fetchOne(0, int.class);
  }


  public void storeAnswers(GameSettingsId gameSettingsId, int round, PlayerId playerId, Map<CategoryId, String> roundAnswers) {
    String playerAnswerId = UUID.randomUUID().toString();
    List<PlayerAnswerRecord> records = roundAnswers.entrySet().stream().map(
      pa ->
        new PlayerAnswerRecord(playerAnswerId, playerId.getId().toString(), pa.getKey().getId().toString(), gameSettingsId.getId().toString(), round, pa.getValue(), 100)
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
