package com.dasred.minecraft_api.api.handler;

import com.dasred.minecraft_api.api.HandlerInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.NotNull;

public class EntitiesHandler implements HandlerInterface {
    public JsonElement createJsonResponse(MinecraftServer mcServer) {
        JsonArray arr = new JsonArray();

        for (ServerLevel level : mcServer.getAllLevels()) {
            String dimensionId = level.dimension().location().toString();

            for (Entity entity : level.getEntities().getAll()) {
                JsonObject objEntity = new JsonObject();
                objEntity.addProperty("uuid", entity.getStringUUID());
                objEntity.addProperty("type", entity.getType().builtInRegistryHolder().key().location().toString());
                objEntity.addProperty("category", entity instanceof ItemEntity ? "item" : entity.getType().getCategory().getName());
                objEntity.addProperty("name", entity.getType().getDescription().getString());
                objEntity.addProperty("customName", entity.getCustomName() == null ? null : entity.getCustomName().getString());
                objEntity.addProperty("dimension", dimensionId);
                objEntity.addProperty("x", entity.getX());
                objEntity.addProperty("y", entity.getY());
                objEntity.addProperty("z", entity.getZ());

                objEntity.add("born", getNBTDataBorn(entity, level));
                objEntity.add("tags", getTags(entity));
                objEntity.add("chunk", ChunksActiveHandler.getJsonForChunk(new ChunkPos(entity.blockPosition()), level));

                if (entity instanceof ItemEntity) {
                    objEntity.add("item", getJsonForItem(((ItemEntity) entity).getItem()));
                }

                arr.add(objEntity);
            }
        }

        return arr;
    }

    private static @NotNull JsonElement getTags(Entity entity) {
        JsonArray tags = new JsonArray();
        for (String tag : entity.getTags()) {
            tags.add(tag);
        }

        return tags;
    }

    private static @NotNull JsonElement getJsonForItem(ItemStack itemStack) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", itemStack.getItem().getName().getString());
        obj.addProperty("type", itemStack.getItem().builtInRegistryHolder().key().location().toString());
        obj.addProperty("count", itemStack.getCount());

        return obj;
    }

    private static JsonElement getNBTDataBorn(Entity entity, ServerLevel level) {
        CompoundTag root = entity.getPersistentData();
        CompoundTag api = root.getCompoundOrEmpty("minecraft_api");

        CompoundTag born = api.getCompoundOrEmpty("born");
        long bornTimestamp = born.getLongOr("timestamp", System.currentTimeMillis());
        long bornGametime = born.getLongOr("gameTime", level.getGameTime());

        JsonObject jsonAt = new JsonObject();
        jsonAt.addProperty("timestamp", bornTimestamp);
        jsonAt.addProperty("gameTime", bornGametime);

        JsonObject result = new JsonObject();
        result.add("at", jsonAt);

        return result;
    }
}
