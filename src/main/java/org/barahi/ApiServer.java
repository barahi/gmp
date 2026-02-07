package org.barahi;

import org.barahi.infra.InfraBinder;
import org.barahi.server.resource.DummyResource;
import org.barahi.server.resource.PlayerResource;
import org.barahi.server.resource.ServiceLocatorFeature;
import org.barahi.server.resource.socket.SocketResource;
import org.barahi.server.resource.WebSocketBinder;
import org.barahi.server.serializer.SerializerBinder;
import org.barahi.service.ServiceBinder;
import org.barahi.store.StoreBinder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;

public class ApiServer {
    // Base uri needs to be pulled in from configuration files
    // We can make this change before we implement deployment scripts
    public static final String BASE_URI = "http://localhost:8080/";
    private static Server webSocketServer;

    public static HttpServer startServer() {
        ResourceConfig resourceConfig = new ResourceConfig()
                .register(JacksonFeature.class)
                .register(InfraBinder.class)
                .register(StoreBinder.class)
                .register(ServiceBinder.class)
                .register(SerializerBinder.class)
                .register(WebSocketBinder.class)  // Register WebSocket endpoints for DI
                .register(ServiceLocatorFeature.class)  // Capture ServiceLocator for WebSocket DI
                .register(DummyResource.class)
                .register(PlayerResource.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }

    public static Server startWebSocketServer() {
        System.out.println("Attempting to start WebSocket server on port 8081...");

        webSocketServer = new Server("localhost", 8081, "/", null, SocketResource.class);
        try {
            webSocketServer.start();
            System.out.println("✓ WebSocket server started successfully at ws://localhost:8081/ws/chat");
            System.out.println("  - Endpoint class: " + SocketResource.class.getName());
            System.out.println("  - Path: /ws/tootiefrootie/:player_id");
        } catch (DeploymentException e) {
            System.err.println("✗ Failed to start WebSocket server: " + e.getMessage());
            e.printStackTrace();
        }
        return webSocketServer;
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        startWebSocketServer();
        System.out.printf("Jersey app started with endpoints available at "
                + "%s%nWebSocket endpoint available at ws://localhost:8081/ws/chat%nHit Ctrl-C to stop it...%n", BASE_URI);
        System.in.read();
        server.stop();
        if (webSocketServer != null) {
            webSocketServer.stop();
        }
    }
}
