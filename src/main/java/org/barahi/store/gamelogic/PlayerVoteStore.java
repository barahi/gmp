package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import static org.barahi.generated.Tables.*;
public class PlayerVoteStore {
  private final DSLContext db;

  @Inject
  public PlayerVoteStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }

  public boolean isAnswerVerified(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId){
    int approvedVotes = db.selectCount()
      .from(PLAYER_VOTE)
      .where(PLAYER_VOTE.TARGET_PLAYER_ID.eq(targetPlayerId.getId().toString()))
      .and(PLAYER_VOTE.ROOM_ID.eq(roomId.getId().toString()))
      .and(PLAYER_VOTE.CATEGORY_ID.eq(categoryId.getId().toString()))
      .and(PLAYER_VOTE.ROUND.eq(roundNumber))
      .and(PLAYER_VOTE.IS_VALID.eq(true))
      .execute();

    int totalVotes = db.selectCount()
      .from(ROOM_PLAYER)
      .where(ROOM_PLAYER.ROOM_ID.eq(roomId.getId().toString()))
      .execute();

    return approvedVotes >= (totalVotes-1)/2.0;
  }

}
