package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.service.gamelogic.Dto.PlayerAnswer;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;
import org.jooq.DSLContext;
import org.jooq.Record1;

import java.util.UUID;

import static org.barahi.generated.Tables.*;
public class PlayerVoteStore {
  private final DSLContext db;

  @Inject
  public PlayerVoteStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }

  public void flagPlayerAnswer(RoomId roomId, String playerAnswerId, PlayerId flaggerPlayerId){
    String id = UUID.randomUUID().toString();
    db.insertInto(FLAG_EVENT)
      .set(FLAG_EVENT.ID, id)
      .set(FLAG_EVENT.PLAYER_ANSWER_ID, playerAnswerId)
      .set(FLAG_EVENT.FLAGGER_PLAYER_ID, flaggerPlayerId.getId().toString())
      .execute();
  }

  public void savePlayerVote(RoomId roomId, CategoryId categoryId, int roundNumber, PlayerId targetPlayerId, PlayerId voterId, boolean isValid){
    String playerAnswerId = db.select(PLAYER_ANSWER.ID)
        .from(PLAYER_ANSWER)
          .where(PLAYER_ANSWER.CATEGORY_ID.eq(categoryId.getId().toString()))
            .and(PLAYER_ANSWER.ROUND.eq(roundNumber))
              .and(PLAYER_ANSWER.PLAYER_ID.eq(targetPlayerId.getId().toString()))
                .fetchOne(PLAYER_ANSWER.ID);

      db.insertInto(PLAYER_VOTE)
      .set(PLAYER_VOTE.ID, UUID.randomUUID().toString())
      .set(PLAYER_VOTE.ROOM_ID, roomId.getId().toString())
      .set(PLAYER_VOTE.PLAYER_ANSWER_ID, playerAnswerId)
      .set(PLAYER_VOTE.VOTER_ID, voterId.getId().toString())
      .set(PLAYER_VOTE.IS_VALID, isValid)
      .execute();
  }

  public VoteRoundResults getVoteRoundResults(RoomId roomId, CategoryId categoryId, String category, int roundNumber, PlayerId targetPlayerId, String targetPlayer){
    String playerAnswerId = db.select(PLAYER_ANSWER.ID)
      .from(PLAYER_ANSWER)
      .where(PLAYER_ANSWER.CATEGORY_ID.eq(categoryId.getId().toString()))
      .and(PLAYER_ANSWER.ROUND.eq(roundNumber))
      .and(PLAYER_ANSWER.PLAYER_ID.eq(targetPlayerId.getId().toString()))
      .fetchOne(PLAYER_ANSWER.ID);


    int approvedVotes = db.selectCount()
      .from(PLAYER_VOTE)
      .where(PLAYER_VOTE.PLAYER_ANSWER_ID.eq(playerAnswerId))
      .and(PLAYER_VOTE.ROOM_ID.eq(roomId.getId().toString()))
      .and(PLAYER_VOTE.IS_VALID.eq(true))
      .fetchOptional()
      .map(Record1::value1)
      .orElse(0);

    int disapprovingVotes = db.selectCount()
      .from(PLAYER_VOTE)
      .where(PLAYER_VOTE.PLAYER_ANSWER_ID.eq(playerAnswerId))
      .and(PLAYER_VOTE.ROOM_ID.eq(roomId.getId().toString()))
      .and(PLAYER_VOTE.IS_VALID.eq(false))
      .fetchOptional()
      .map(Record1::value1)
      .orElse(0);


    return new VoteRoundResults(
      category,
      roundNumber,
      targetPlayer,
      approvedVotes,
      disapprovingVotes
    );
  }

}
