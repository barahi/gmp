package org.barahi.server.resource;

import org.glassfish.hk2.api.ServiceLocator;

import javax.websocket.server.ServerEndpointConfig;

public class GuiceWebSocketConfigurator extends ServerEndpointConfig.Configurator {

    private static ServiceLocator serviceLocator;

    public static void setServiceLocator(ServiceLocator serviceLocator) {
        GuiceWebSocketConfigurator.serviceLocator = serviceLocator;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (serviceLocator == null) {
            throw new InstantiationException("HK2 ServiceLocator not configured for WebSocket endpoints");
        }
        T instance = serviceLocator.getService(endpointClass);
        if (instance == null) {
            // Fallback to creating instance with dependency injection
            instance = serviceLocator.createAndInitialize(endpointClass);
        }
        return instance;
    }
}

