package com.dasred.minecraft_api.api.handler;

import com.dasred.minecraft_api.api.HandlerInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.border.WorldBorder;

public class DimensionsHandler implements HandlerInterface {
    public JsonElement createJsonResponse(MinecraftServer mcServer) {
        JsonArray jsonDimensions = new JsonArray();
        for (ServerLevel level : mcServer.getAllLevels()) {
            JsonObject jsonDimension = new JsonObject();

            jsonDimension.addProperty("id", level.dimension().location().toString());

            BlockPos spawnPosition = level.getSharedSpawnPos();
            JsonObject jsonSpawn = new JsonObject();
            jsonSpawn.addProperty("x", spawnPosition.getX());
            jsonSpawn.addProperty("y", spawnPosition.getY());
            jsonSpawn.addProperty("z", spawnPosition.getZ());
            jsonDimension.add("spawn", jsonSpawn);

            WorldBorder worldBorder = level.getWorldBorder();
            JsonObject jsonWorldBorder = new JsonObject();

            JsonObject jsonWorldBorderX = new JsonObject();
            jsonWorldBorderX.addProperty("min", worldBorder.getMinX());
            jsonWorldBorderX.addProperty("max", worldBorder.getMaxX());
            jsonWorldBorder.add("x", jsonWorldBorderX);

            JsonObject jsonWorldBorderZ = new JsonObject();
            jsonWorldBorderZ.addProperty("min", worldBorder.getMinZ());
            jsonWorldBorderZ.addProperty("max", worldBorder.getMaxZ());
            jsonWorldBorder.add("z", jsonWorldBorderZ);

            JsonObject jsonWorldBorderCenter = new JsonObject();
            jsonWorldBorderCenter.addProperty("x", worldBorder.getCenterX());
            jsonWorldBorderCenter.addProperty("z", worldBorder.getCenterZ());
            jsonWorldBorder.add("center", jsonWorldBorderCenter);

            jsonDimension.add("border", jsonWorldBorder);

            jsonDimensions.add(jsonDimension);
        }

        return jsonDimensions;
    }
}
