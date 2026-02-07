package org.barahi.server.resource;

import org.barahi.server.resource.socket.SocketResource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class WebSocketBinder extends AbstractBinder {
    @Override
    protected void configure() {
        // Bind WebSocket endpoint so HK2 can inject dependencies
        bind(SocketResource.class).to(SocketResource.class);
    }
}

