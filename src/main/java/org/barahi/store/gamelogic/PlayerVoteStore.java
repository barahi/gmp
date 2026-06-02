package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.jooq.Record1;

import static org.barahi.generated.Tables.*;
public class PlayerVoteStore {
  private final DSLContext db;

  @Inject
  public PlayerVoteStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }

  public void savePlayerVote(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, boolean isValid){
    db.insertInto(PLAYER_VOTE)
      .set(PLAYER_VOTE.ROOM_ID, roomId.getId().toString())
      .set(PLAYER_VOTE.CATEGORY_ID, categoryId.getId().toString())
      .set(PLAYER_VOTE.ROUND, roundNumber)
      .set(PLAYER_VOTE.TARGET_PLAYER_ID, targetPlayerId.getId().toString())
      .set(PLAYER_VOTE.VOTER_ID, voterId.getId().toString())
      .set(PLAYER_VOTE.IS_VALID, isValid)
      .execute();
  }

  public VoteRoundResults getVoteRoundResults(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId){
    int approvedVotes = db.selectCount()
      .from(PLAYER_VOTE)
      .where(PLAYER_VOTE.TARGET_PLAYER_ID.eq(targetPlayerId.getId().toString()))
      .and(PLAYER_VOTE.ROOM_ID.eq(roomId.getId().toString()))
      .and(PLAYER_VOTE.CATEGORY_ID.eq(categoryId.getId().toString()))
      .and(PLAYER_VOTE.ROUND.eq(roundNumber))
      .and(PLAYER_VOTE.IS_VALID.eq(true))
      .fetchOptional()
      .map(Record1::value1)
      .orElse(0);

    int disapprovingVotes = db.selectCount()
      .from(PLAYER_VOTE)
      .where(PLAYER_VOTE.TARGET_PLAYER_ID.eq(targetPlayerId.getId().toString()))
      .and(PLAYER_VOTE.ROOM_ID.eq(roomId.getId().toString()))
      .and(PLAYER_VOTE.CATEGORY_ID.eq(categoryId.getId().toString()))
      .and(PLAYER_VOTE.ROUND.eq(roundNumber))
      .and(PLAYER_VOTE.IS_VALID.eq(false))
      .fetchOptional()
      .map(Record1::value1)
      .orElse(0);


    return new VoteRoundResults(
      categoryId,
      roundNumber,
      targetPlayerId,
      approvedVotes,
      disapprovingVotes
    );
  }

}
