package com.dasred.minecraft_api.api;

import com.dasred.minecraft_api.api.handler.DimensionsHandler;
import com.dasred.minecraft_api.api.handler.EntitiesHandler;
import com.dasred.minecraft_api.api.handler.PlayersHandler;
import com.dasred.minecraft_api.api.handler.RootHandler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.server.MinecraftServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class HttpApiServer {
    private static HttpServer server;
    private static final Gson gson = new Gson();

    private static final Map<String, Endpoint> ENDPOINTS = Map.of(
            "entities", new Endpoint("entities", "/entities", false, new EntitiesHandler()),
            "players", new Endpoint("players", "/players", true, new PlayersHandler()),
            "dimensions", new Endpoint("dimensions", "/dimensions", true, new DimensionsHandler())
    );

    public static void start(MinecraftServer mcServer, int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            ENDPOINTS.values().forEach(endpoint ->
                    server.createContext(endpoint.path(), new JSONResponseHandler(mcServer, endpoint.handler()))
            );

            server.createContext("/", new JSONResponseHandler(mcServer, new RootHandler(ENDPOINTS)));

            server.start();
            System.out.println("[MinecraftAPI] API started at port " + port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("[MinecraftAPI] API stopped.");
        }
    }
}