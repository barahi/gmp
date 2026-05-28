package org.barahi.server.serializer;

import org.barahi.server.json.SubmitVoteEventPayloadJson;
import org.barahi.server.resource.socket.events.submitvote.SubmitVoteEventPayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

public class SubmitVoteEventPayloadSerializer {
  public SubmitVoteEventPayload fromJson(SubmitVoteEventPayloadJson json){
    SubmitVoteEventPayload payload = new SubmitVoteEventPayload();
    payload.setCategoryId(CategoryId.of(json.getCategoryId()));
    payload.setRoundNumber(json.getRoundNumber());
    payload.setTargetPlayerId(PlayerId.of(json.getTargetPlayerId()));
    payload.setVoterId(PlayerId.of(json.getVoterId()));
    payload.setVote(json.getVote());
    return payload;
  }
}
