package org.barahi.server.serializer;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class SerializerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PlayerSerializer.class).to(PlayerSerializer.class);
    }
}
