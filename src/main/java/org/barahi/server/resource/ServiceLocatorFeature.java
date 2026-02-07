package org.barahi.server.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.barahi.infra.LoggerFactory;
import org.glassfish.hk2.api.ServiceLocator;

import java.util.logging.Logger;

/**
 * Feature to capture the HK2 ServiceLocator for use in WebSocket endpoints
 */
public class ServiceLocatorFeature implements Feature {
    private static final Logger LOGGER = LoggerFactory.createLogger(ServiceLocatorFeature.class);

    @Inject
    private ServiceLocator serviceLocator;

    @Override
    public boolean configure(FeatureContext context) {
        // Capture the ServiceLocator and make it available to WebSocket configurator
        GuiceWebSocketConfigurator.setServiceLocator(serviceLocator);
        LOGGER.info("✓ ServiceLocator captured and configured for WebSocket DI");
        return true;
    }
}

