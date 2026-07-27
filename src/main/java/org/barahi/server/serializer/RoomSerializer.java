package org.barahi.server.serializer;

import org.barahi.server.json.RoomJson;
import org.barahi.service.room.RoomDto;
import org.barahi.serviceapi.gameSettings.GameSettings;
import org.barahi.serviceapi.room.Room;

public class RoomSerializer {
    public RoomJson toJson(Room room, GameSettings gameSettings) {
        RoomJson json = new RoomJson();
        json.setId(room.getId().getId().toString());
        json.setHostPlayerId(room.getHostPlayerId().getId().toString());
        json.setMaxPlayers(gameSettings.getMaxPlayers());
        json.setRoundDuration(gameSettings.getRoundDuration());
        json.setNumberOfRounds(gameSettings.getNumberOfRounds());
        json.setPassword(gameSettings.getPassword());
        json.setCategories(gameSettings.getCategories());
        json.setExcludedLetters(gameSettings.getExcludedLetters());
        json.setGameStarted(room.isGameStarted());
        return json;
    }

    public RoomJson toJson(RoomDto roomDto){
        RoomJson json = new RoomJson();
        json.setId(roomDto.getRoomId().getId().toString());
        json.setHostPlayerId(roomDto.getHostPlayerId().getId().toString());
        json.setMaxPlayers(roomDto.getMaxPlayers());
        json.setRoundDuration(roomDto.getRoundDuration());
        json.setNumberOfRounds(roomDto.getNumberOfRounds());
        json.setPassword(roomDto.getPassword());
        json.setCategories(roomDto.getCategories());
        json.setExcludedLetters(roomDto.getExcludedLetters());
        return json;
    }
}
