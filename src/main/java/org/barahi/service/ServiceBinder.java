package org.barahi.service;

import org.barahi.service.gamesettings.GameSettingsServiceImpl;
import org.barahi.service.lobby.LobbyServiceImpl;
import org.barahi.service.player.PlayerServiceImpl;
import org.barahi.serviceapi.gamesettings.GameSettingsService;
import org.barahi.serviceapi.lobby.LobbyService;
import org.barahi.serviceapi.player.PlayerService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ServiceBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PlayerServiceImpl.class).to(PlayerService.class);
        bind(GameSettingsServiceImpl.class).to(GameSettingsService.class);
        bind(LobbyServiceImpl.class).to(LobbyService.class);
    }
}
