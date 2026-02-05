package org.barahi.server.serializer;

import org.barahi.server.json.RoomJson;
import org.barahi.serviceapi.gameSettings.GameSettings;
import org.barahi.serviceapi.room.Room;

public class RoomSerializer {

    public RoomJson toJson(Room room, GameSettings gameSettings) {
        return new RoomJson()
                .setId(room.getId().getId().toString())
                .setHostPlayerId(room.getHostPlayerId().getId().toString())
                .setMaxPlayers(gameSettings.getMaxPlayers())
                .setRoundDuration(gameSettings.getRoundDuration())
                .setNumberOfRounds(gameSettings.getNumberOfRounds())
                .setCategories(gameSettings.getCategories())
                .setExcludedLetters(gameSettings.getExcludedLetters())
                .setGameStarted(room.isGameStarted());
    }
}
