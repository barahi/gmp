package org.barahi.server.serializer;

import org.barahi.server.json.BeginVotePayloadEventJson;
import org.barahi.server.resource.socket.events.beginvote.BeginVotePhasePayload;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

public class BeginVotePayloadEventSerializer {
  public BeginVotePhasePayload fromJson(BeginVotePayloadEventJson json){
    return new BeginVotePhasePayload(
      CategoryId.of(json.getCategoryId()),
      json.getRoundNumber(),
      PlayerId.of(json.getTargetPlayerId()),
      PlayerId.of(json.getVoterId()),
      json.getAnswer()
    );
  }
}
