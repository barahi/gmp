package org.barahi.infra;

import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@PreMatching
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {
  @Override
  public void filter(ContainerRequestContext context) throws IOException {
    if ("OPTIONS".equalsIgnoreCase(context.getMethod())) {
      Response response = Response.ok()
        .header("Access-Control-Allow-Origin", "http://localhost:3000")
        .header("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS")
        .header("Access-Control-Allow-Headers", "Content-Type, Authorization, CONTADOR_TOKEN")
        .header("Access-Control-Allow-Credentials", "true")
        .build();
      context.abortWith(response);
    }
  }

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "http://localhost:3000");
    responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
    responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization, CONTADOR_TOKEN");
    responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
  }

}
