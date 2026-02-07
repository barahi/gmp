package org.barahi.server.resource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class WebSocketBinder extends AbstractBinder {
    @Override
    protected void configure() {
        // Bind WebSocket endpoint so HK2 can inject dependencies
        bind(SocketResouce.class).to(SocketResouce.class);
    }
}

