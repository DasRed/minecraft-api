package com.dasred.minecraft_api.api;

public record Endpoint(String id, String path, Boolean inRoot, HandlerInterface handler) {
}
