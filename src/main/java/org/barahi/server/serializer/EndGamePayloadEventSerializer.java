package org.barahi.server.serializer;

import org.barahi.server.json.EndGamePayloadJson;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndGamePayloadEventSerializer {
  public EndGamePayloadJson toJson(List<String> winners){
    EndGamePayloadJson json = new EndGamePayloadJson();
    json.setGameWinner(winners);
    return json;
  }
}
