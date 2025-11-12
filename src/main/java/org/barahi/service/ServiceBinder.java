package org.barahi.service;

import org.barahi.service.player.PlayerServiceImpl;
import org.barahi.serviceapi.player.PlayerService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ServiceBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PlayerServiceImpl.class).to(PlayerService.class);
    }
}
