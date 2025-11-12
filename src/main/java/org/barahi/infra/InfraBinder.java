package org.barahi.infra;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class InfraBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(DSLContextProvider.class).to(DSLContextProvider.class);
    }
}