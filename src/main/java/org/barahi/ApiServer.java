package org.barahi;

import org.barahi.infra.InfraBinder;
import org.barahi.server.resource.DummyResource;
import org.barahi.server.resource.PlayerResource;
import org.barahi.server.serializer.SerializerBinder;
import org.barahi.service.ServiceBinder;
import org.barahi.store.StoreBinder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class ApiServer {
    // Base uri needs to be pulled in from configuration files
    // We can make this change before we implement deployment scripts
    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig()
                .register(JacksonFeature.class)
                .register(InfraBinder.class)
                .register(StoreBinder.class)
                .register(ServiceBinder.class)
                .register(SerializerBinder.class)
                .register(DummyResource.class)
                .register(PlayerResource.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.printf("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...%n", BASE_URI);
        System.in.read();
        server.stop();
    }
}
