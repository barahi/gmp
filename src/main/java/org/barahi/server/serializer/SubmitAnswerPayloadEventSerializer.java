package org.barahi.server.serializer;

import org.barahi.server.json.SubmitAnswerPayloadJson;
import org.barahi.server.resource.socket.events.submitanswers.SubmitAnswersEventPayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.Map;

public class SubmitAnswerPayloadEventSerializer {
  public SubmitAnswerPayloadJson toJson(SubmitAnswersEventPayload payload){
    SubmitAnswerPayloadJson json = new SubmitAnswerPayloadJson();
    json.setRoundNumber(payload.getRoundNumber());
    json.setPlayerId(payload.getPlayerId().getId().toString());
    json.setRoundAnswers(payload.getRoundAnswers());
    return json;
  }

  public SubmitAnswersEventPayload fromJson(SubmitAnswerPayloadJson json){
    return new SubmitAnswersEventPayload(
      json.getRoundNumber(),
      PlayerId.of(json.getPlayerId()),
      json.getRoundAnswers()
    );
  }
}
