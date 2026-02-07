package org.barahi.server.resource;

import org.barahi.serviceapi.player.Player;
import org.barahi.serviceapi.player.PlayerService;

import jakarta.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/chat", configurator = GuiceWebSocketConfigurator.class)
public class SocketResource {

    private static final Set<javax.websocket.Session> SESSIONS = new CopyOnWriteArraySet<>();

    private final PlayerService playerService;

    @Inject
    public SocketResource(PlayerService playerService) {
        this.playerService = playerService;
        System.out.println("SocketResource created with PlayerService: " + (playerService != null ? "SUCCESS" : "NULL"));
    }

    @OnOpen
    public void onOpen(javax.websocket.Session session) {
        System.out.println("=== WebSocket @OnOpen triggered ===");
        System.out.println("Session ID: " + session.getId());
        SESSIONS.add(session);
        System.out.println("New WebSocket connection: " + session.getId());
        System.out.println("Total active sessions: " + SESSIONS.size());
        broadcastMessage("Server: " + session.getId() + " connected. Total users: " + SESSIONS.size());
    }

    @OnMessage
    public void onMessage(String message, javax.websocket.Session session) throws IllegalAccessException {
        System.out.println("Received message from " + session.getId() + ": " + message);
        Player player = playerService.getPlayer(new Player.PlayerId(UUID.fromString(message)));
        broadcastMessage(session.getId() + ": " + player.getUsername());
    }

    @OnClose
    public void onClose(javax.websocket.Session session) {
        SESSIONS.remove(session);
        System.out.println("WebSocket connection closed: " + session.getId());
        broadcastMessage("Server: " + session.getId() + " disconnected. Total users: " + SESSIONS.size());
    }

    @OnError
    public void onError(javax.websocket.Session session, Throwable error) {
        System.err.println("WebSocket error in " + session.getId() + ": " + error.getMessage());
        error.printStackTrace();
    }

    private void broadcastMessage(String message) {
        for (javax.websocket.Session session : SESSIONS) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.err.println("Failed to send message to " + session.getId() + ": " + e.getMessage());
                }
            }
        }
    }
}
