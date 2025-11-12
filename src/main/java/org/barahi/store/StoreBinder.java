package org.barahi.store;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class StoreBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PlayerStore.class).to(PlayerStore.class);
    }
}
