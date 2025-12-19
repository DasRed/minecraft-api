package com.dasred.minecraft_api.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.io.OutputStream;

public class JSONResponseHandler implements HttpHandler {
    private final MinecraftServer mcServer;
    private final HandlerInterface handler;
    private static final Gson gson = new Gson();

    public JSONResponseHandler(MinecraftServer mcServer, HandlerInterface handler) {
        this.mcServer = mcServer;
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        JsonElement jsonResponse = handler.createJsonResponse(mcServer);
        byte[] json = gson.toJson(jsonResponse).getBytes();

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, json.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json);
        }
    }
}
