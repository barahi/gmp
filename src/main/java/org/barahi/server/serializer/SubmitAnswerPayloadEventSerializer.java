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
    json.setCategoryId(payload.getCategoryId().getId().toString());
    json.setRoundNumber(payload.getRoundNumber());
    Map<String, String> map = new HashMap<>();
    if (payload.getPlayerAnswers() != null ){
      payload.getPlayerAnswers().forEach((k, v) -> {
        map.put(k.getId().toString(), v);
      });
    }
    json.setPlayerAnswers(map);
    return json;
  }

  public SubmitAnswersEventPayload fromJson(SubmitAnswerPayloadJson json){
    Map<PlayerId, String> map = new HashMap<>();
    if (json.getPlayerAnswers() != null){
      json.getPlayerAnswers().forEach((k,v) -> {
        map.put(PlayerId.of(k), v);
      });
    }
    return new SubmitAnswersEventPayload(
      CategoryId.of(json.getCategoryId()),
      json.getRoundNumber(),
      map
    );
  }
}
