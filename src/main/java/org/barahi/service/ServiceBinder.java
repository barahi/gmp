package org.barahi.service;

import org.barahi.service.gamelogic.*;
import org.barahi.service.player.PlayerServiceImpl;
import org.barahi.service.room.RoomServiceImpl;
import org.barahi.serviceapi.player.PlayerService;
import org.barahi.serviceapi.room.RoomService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ServiceBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PlayerServiceImpl.class).to(PlayerService.class);
        bind(RoomServiceImpl.class).to(RoomService.class);
        bind(RoundLogicServiceImpl.class).to(RoundLogicService.class);
        bind(GameLogicServiceImpl.class).to(GameLogicService.class);
        bind(GameCoordinatorImpl.class).to(GameCoordinator.class);
        bind(GameScheduler.class).to(GameScheduler.class);
    }
}
