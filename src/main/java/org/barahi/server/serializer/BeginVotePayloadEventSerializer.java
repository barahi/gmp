package org.barahi.server.serializer;

import org.barahi.server.json.BeginVotePayloadJson;
import org.barahi.server.json.FlaggedAnswerPayloadJson;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhasePayload;
import org.barahi.service.gamelogic.Dto.FlaggedAnswer;
import org.barahi.serviceapi.player.Player.PlayerId;

public class BeginVotePayloadEventSerializer {
  public BeginVotePhasePayload fromJson(BeginVotePayloadJson json){
    return new BeginVotePhasePayload(
      json.getCategory(),
      json.getRoundNumber(),
      PlayerId.of(json.getTargetPlayerId()),
      PlayerId.of(json.getVoterId()),
      json.getAnswer()
    );
  }

  public FlaggedAnswerPayloadJson toJson(FlaggedAnswer flaggedAnswer){
    FlaggedAnswerPayloadJson json = new FlaggedAnswerPayloadJson();
    json.setCategory(flaggedAnswer.getCategory());
    json.setFlaggedPlayerId(flaggedAnswer.getFlaggedPlayerId().getId().toString());
    json.setFlaggerPlayerId(flaggedAnswer.getFlaggerPlayerId().getId().toString());
    json.setAnswer(flaggedAnswer.getAnswer());
    json.setScore(flaggedAnswer.getScore());
    return json;
  }
}
