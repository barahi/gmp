package org.barahi.server.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.ServiceLocator;

/**
 * Feature to capture the HK2 ServiceLocator for use in WebSocket endpoints
 */
public class ServiceLocatorFeature implements Feature {

    @Inject
    private ServiceLocator serviceLocator;

    @Override
    public boolean configure(FeatureContext context) {
        // Capture the ServiceLocator and make it available to WebSocket configurator
        GuiceWebSocketConfigurator.setServiceLocator(serviceLocator);
        System.out.println("✓ ServiceLocator captured and configured for WebSocket DI");
        return true;
    }
}

