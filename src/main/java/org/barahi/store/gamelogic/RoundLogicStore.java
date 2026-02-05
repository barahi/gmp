package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Map;
import static org.barahi.generated.Tables.*;

public class RoundLogicStore {
  private final DSLContext db;

  @Inject
  public RoundLogicStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

  public void storeAnswers(Map<String, String> playerAnswers, String category){
    for (Map.Entry<String, String> answer: playerAnswers.entrySet()){
      db.update(PLAYER_ANSWER)
        .set(PLAYER_ANSWER.PLAYER_ID, answer.getKey())
        .set(PLAYER_ANSWER.ANSWER, answer.getValue())
        .where(PLAYER_ANSWER.CATEGORY.eq(category))
        .execute();
    }
  }

  public void storePlayerScores(Map<String, Integer> playerScores, String category){
    for (Map.Entry<String, Integer> score: playerScores.entrySet()){
      db.update(PLAYER_ANSWER)
        .set(PLAYER_ANSWER.PLAYER_ID, score.getKey())
        .set(PLAYER_ANSWER.SCORE, score.getValue())
        .where(PLAYER_ANSWER.CATEGORY.eq(category))
        .execute();
    }
  }

  public Map<String, Integer> endRound(String category){
    return db.select(PLAYER_ANSWER.PLAYER_ID, PLAYER_ANSWER.SCORE)
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.CATEGORY.eq(category))
      .fetchMap(
        PLAYER_ANSWER.PLAYER_ID,
        PLAYER_ANSWER.SCORE
      );
  }

}
