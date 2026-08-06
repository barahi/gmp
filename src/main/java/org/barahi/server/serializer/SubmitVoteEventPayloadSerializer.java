package org.barahi.server.serializer;

import org.barahi.server.json.SubmitVotePayloadJson;
import org.barahi.server.resource.socket.events.submitvote.SubmitVoteEventPayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

public class SubmitVoteEventPayloadSerializer {
  public SubmitVoteEventPayload fromJson(SubmitVotePayloadJson json){
    SubmitVoteEventPayload payload = new SubmitVoteEventPayload();
    payload.setCategory(json.getCategory());
    payload.setTargetPlayer(json.getTargetPlayer());
    payload.setVoterPlayer(json.getVoterPlayer());
    payload.setVote(json.getVote());
    return payload;
  }
}
