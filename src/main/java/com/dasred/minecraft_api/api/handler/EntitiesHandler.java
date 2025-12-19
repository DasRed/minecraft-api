package com.dasred.minecraft_api.api.handler;

import com.dasred.minecraft_api.api.HandlerInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class EntitiesHandler implements HandlerInterface {
    public JsonElement createJsonResponse(MinecraftServer mcServer) {
        JsonArray arr = new JsonArray();

        for (ServerLevel level : mcServer.getAllLevels()) {
            String dimensionId = level.dimension().location().toString();

            for (Entity entity : level.getEntities().getAll()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("uuid", entity.getStringUUID());
                obj.addProperty("type", entity.getType().builtInRegistryHolder().key().location().toString());
                obj.addProperty("category", entity instanceof ItemEntity ? "item" : entity.getType().getCategory().getName());
                obj.addProperty("name", entity.getType().getDescription().getString());
                obj.addProperty("customName", entity.getCustomName() == null ? null : entity.getCustomName().getString());
                obj.addProperty("dimension", dimensionId);
                obj.addProperty("x", entity.getX());
                obj.addProperty("y", entity.getY());
                obj.addProperty("z", entity.getZ());
                obj.addProperty("onGround", entity.onGround());

                obj.addProperty("chunkLoaded", level.isLoaded(entity.blockPosition()));

                if (entity instanceof ItemEntity) {
                    JsonObject objItem = new JsonObject();
                    ItemStack itemStack = ((ItemEntity) entity).getItem();
                    objItem.addProperty("name", itemStack.getItem().getName().getString());
                    objItem.addProperty("type", itemStack.getItem().builtInRegistryHolder().key().location().toString());
                    objItem.addProperty("age", ((ItemEntity) entity).getAge());
                    objItem.addProperty("lifespan", ((ItemEntity) entity).lifespan);
                    objItem.addProperty("count", itemStack.getCount());
                    objItem.addProperty("popTime", itemStack.getPopTime());
                    obj.add("item", objItem);
                }

                JsonArray tags = new JsonArray();
                for (String tag : entity.getTags()) {
                    tags.add(tag);
                }
                obj.add("tags", tags);

                arr.add(obj);
            }
        }

        return arr;
    }
}
