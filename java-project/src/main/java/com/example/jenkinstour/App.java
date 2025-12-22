package com.example.jenkinstour;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public final class App {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/health", new HealthHandler());
        server.createContext("/", new RootHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started on http://localhost:" + PORT);
    }

    private static final class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] body = "OK".getBytes(StandardCharsets.UTF_8);
            respond(exchange, 200, body);
        }
    }

    private static final class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String message = "jenkins-tour backend running: " + Instant.now();
            byte[] body = message.getBytes(StandardCharsets.UTF_8);
            respond(exchange, 200, body);
        }
    }

    private static void respond(HttpExchange exchange, int status, byte[] body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(status, body.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body);
        }
    }
}
