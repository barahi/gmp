package org.barahi.server.serializer;

import org.barahi.server.json.BeginVotePayloadJson;
import org.barahi.server.json.FlaggedAnswerPayloadJson;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhasePayload;
import org.barahi.service.gamelogic.Dto.FlaggedAnswer;
public class BeginVotePayloadEventSerializer {
  public BeginVotePhasePayload fromJson(BeginVotePayloadJson json){
    return new BeginVotePhasePayload(
      json.getCategory(),
      json.getTargetedPlayer(),
      json.getTriggeredByPlayer(),
      json.getAnswer()
    );
  }

  public FlaggedAnswerPayloadJson toJson(FlaggedAnswer flaggedAnswer){
    FlaggedAnswerPayloadJson json = new FlaggedAnswerPayloadJson();
    json.setCategory(flaggedAnswer.getCategory());
    json.setTargetedPlayer(flaggedAnswer.getTargetedPlayer());
    json.setTriggeredByPlayer(flaggedAnswer.getTriggeredByPlayer());
    json.setAnswer(flaggedAnswer.getAnswer());
    json.setScore(flaggedAnswer.getScore());
    return json;
  }
}
