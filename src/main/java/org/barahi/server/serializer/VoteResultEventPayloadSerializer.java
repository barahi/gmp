package org.barahi.server.serializer;

import org.barahi.server.json.VoteResultEventPayloadJson;
import org.barahi.server.resource.socket.events.voteresult.VoteResultEventPayload;

public class VoteResultEventPayloadSerializer {
  private VoteResultEventPayloadJson toJson(VoteResultEventPayload payload){
    VoteResultEventPayloadJson json = new VoteResultEventPayloadJson();
    json.setCategoryId(payload.getCategoryId().getId().toString());
    json.setRoundNumber(payload.getRoundNumber());
    json.setTargetPlayerId(payload.getTargetPlayerId().getId().toString());
    json.setValidAnswerVotes(payload.getValidAnswerVotes());
    json.setInvalidAnswerVotes(payload.getInvalidAnswerVotes());
    return json;
  }
}
