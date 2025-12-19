package com.dasred.minecraft_api.api;

import com.google.gson.JsonElement;
import net.minecraft.server.MinecraftServer;

public interface HandlerInterface {
    JsonElement createJsonResponse(MinecraftServer mcServer);
}
