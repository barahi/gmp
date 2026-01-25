package org.barahi.server.serializer;

import org.barahi.server.json.GameSettingsJson;
import org.barahi.serviceapi.gamesettings.GameSettings;
import org.barahi.serviceapi.gamesettings.GameSettingsImpl;

public class GameSettingsSerializer {
    public GameSettingsJson toJson(GameSettings gameSettings){
        GameSettingsJson json = new GameSettingsJson();
        json.setPlayerCount(gameSettings.getPlayerCount());
        json.setCategories(gameSettings.getCategories());
        json.setRoundDuration(gameSettings.getRoundDuration());
        json.setNumberOfRounds(gameSettings.getNumberOfRounds());
        json.setPassword(gameSettings.getPassword());
        json.setExcludedLetters(gameSettings.getExcludedLetters());
        return json;
    }
    public GameSettings fromJson(GameSettingsJson json){
        return new GameSettingsImpl(
            null,  
            null,   
            json.getPlayerCount(), 
            json.getCategories(), 
            json.getRoundDuration(), 
            json.getNumberOfRounds(), 
            json.getPassword(), 
            json.getExcludedLetters()); 
        }
}
