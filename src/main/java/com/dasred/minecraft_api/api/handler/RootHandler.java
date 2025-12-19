package com.dasred.minecraft_api.api.handler;

import com.dasred.minecraft_api.api.Endpoint;
import com.dasred.minecraft_api.api.HandlerInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;

import java.util.Map;

public class RootHandler implements HandlerInterface {
    private final Map<String, Endpoint> endpoints;

    public RootHandler(Map<String, Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public JsonElement createJsonResponse(MinecraftServer mcServer) {
        JsonObject jsonResponse = new JsonObject();

        endpoints.values().forEach(endpoint -> {
            if (endpoint.inRoot()) {
                jsonResponse.add(endpoint.id(), endpoint.handler().createJsonResponse(mcServer));
            }
        });

        return jsonResponse;
    }
}
