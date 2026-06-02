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
    Map<String, String> map = new HashMap<>();
    if (payload.getRoundAnswers() != null ){
      payload.getRoundAnswers().forEach((k, v) -> {
        map.put(k.getId().toString(), v);
      });
    }
    json.setRoundAnswers(map);
    return json;
  }

  public SubmitAnswersEventPayload fromJson(SubmitAnswerPayloadJson json){
    Map<CategoryId, String> map = new HashMap<>();
    if (json.getRoundAnswers() != null){
      json.getRoundAnswers().forEach((k,v) -> {
        map.put(CategoryId.of(k), v);
      });
    }
    return new SubmitAnswersEventPayload(
      json.getRoundNumber(),
      PlayerId.of(json.getPlayerId()),
      map
    );
  }
}
