package com.dasred.minecraft_api.api.handler;

import com.dasred.minecraft_api.api.HandlerInterface;
import com.dasred.minecraft_api.chunk.ChunkTracker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

public class ChunksActiveHandler implements HandlerInterface {
    public JsonElement createJsonResponse(MinecraftServer mcServer) {
        JsonArray arr = new JsonArray();

        for (ServerLevel level : mcServer.getAllLevels()) {
            for (ChunkPos pos : ChunkTracker.getLoaded(level)) {
                arr.add(getJsonForChunk(pos, level));
            }
        }

        return arr;
    }

    public static @NotNull JsonElement getJsonForChunk(ChunkPos pos, ServerLevel level) {
        LevelChunk chunk = level.getChunkSource().getChunk(pos.x, pos.z, false);

        JsonObject objChunk = new JsonObject();
        objChunk.addProperty("id", level.dimension().location() + "[" + pos.getMinBlockX() + " | " + pos.getMinBlockZ() + "] - [" + pos.getMaxBlockX() + " | " + pos.getMaxBlockZ() + "]");
        objChunk.addProperty("dimension", level.dimension().location().toString());
        objChunk.addProperty("status", chunk != null ? chunk.getFullStatus().toString() : "UNKNOWN");

        JsonObject objChunkPos = new JsonObject();
        objChunkPos.add("min", getJsonForChunkPosition(pos.getMinBlockX(), pos.getMinBlockZ()));
        objChunkPos.add("max", getJsonForChunkPosition(pos.getMaxBlockX(), pos.getMaxBlockZ()));

        objChunk.add("position", objChunkPos);

        return objChunk;
    }

    private static @NotNull JsonElement getJsonForChunkPosition(int x, int z) {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", x);
        obj.addProperty("z", z);

        return obj;
    }
}
