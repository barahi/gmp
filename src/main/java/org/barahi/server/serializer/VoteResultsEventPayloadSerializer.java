package org.barahi.server.serializer;

import org.barahi.server.json.VoteResultsPayloadJson;
import org.barahi.service.gamelogic.Dto.VoteRoundResults;

public class VoteResultsEventPayloadSerializer {
  public VoteResultsPayloadJson toJson(VoteRoundResults voteRoundResults){
    VoteResultsPayloadJson json = new VoteResultsPayloadJson();
    json.setCategory(voteRoundResults.getCategory());
    json.setRoundNumber(voteRoundResults.getRoundNumber());
    json.setTargetPlayerId(voteRoundResults.getTargetPlayerId().getId().toString());
    json.setValidAnswerVotes(voteRoundResults.getValidAnswerVotes());
    json.setInvalidAnswerVotes(voteRoundResults.getInvalidAnswerVotes());
    return json;
  }
}
