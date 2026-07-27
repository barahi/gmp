package org.barahi.server.resource.socket.events.playerjoined;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.barahi.infra.Functional;
import org.barahi.server.json.PlayerJson;
import org.barahi.server.json.RoomJson;
import org.barahi.server.resource.socket.EventPayload;
import org.barahi.server.serializer.PlayerSerializer;
import org.barahi.server.serializer.RoomSerializer;
import org.barahi.service.room.RoomDto;
import org.barahi.serviceapi.player.Player;

import java.util.List;

public class PlayerJoinedEventPayload implements EventPayload {
    private static final PlayerSerializer PLAYER_SERIALIZER = new PlayerSerializer();
    private static final RoomSerializer ROOM_SERIALIZER = new RoomSerializer();
    private List<Player> players;
    private RoomDto roomDto;

    @JsonProperty("players")
    public List<PlayerJson> getPlayers() {
        return Functional.map(players, PLAYER_SERIALIZER::toJson);
    }

    @JsonProperty("settings")
    public RoomJson getSettings(){
       return ROOM_SERIALIZER.toJson(roomDto);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setSettings(RoomDto roomDto){
        this.roomDto = roomDto;
    }
}
