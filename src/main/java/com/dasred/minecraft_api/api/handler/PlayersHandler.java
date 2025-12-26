package com.dasred.minecraft_api.api.handler;

import com.dasred.minecraft_api.api.HandlerInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlayersHandler implements HandlerInterface {
    public JsonElement createJsonResponse(MinecraftServer mcServer) {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("max", mcServer.getMaxPlayers());

        JsonObject jsonPlayers = new JsonObject();
        for (ServerPlayer player : mcServer.getPlayerList().getPlayers()) {
            JsonObject jsonPlayer = new JsonObject();
            jsonPlayer.addProperty("uuid", player.getStringUUID());
            jsonPlayer.addProperty("name", player.getGameProfile().getName());
            jsonPlayer.addProperty("dimension", player.level().dimension().location().toString());
            jsonPlayer.addProperty("x", player.getX());
            jsonPlayer.addProperty("y", player.getY());
            jsonPlayer.addProperty("z", player.getZ());
            jsonPlayer.addProperty("yHeadRot", player.getYHeadRot());
            jsonPlayer.addProperty("armor", armorPoints(player));
            jsonPlayer.addProperty("health", player.getHealth());
            // net.minecraft.world.level.GameType.SURVIVAL = 0
            // net.minecraft.world.level.GameType.CREATIVE = 1
            // net.minecraft.world.level.GameType.ADVENTURE = 2
            // net.minecraft.world.level.GameType.SPECTATOR = 3
            jsonPlayer.addProperty("gameMode", player.gameMode.getGameModeForPlayer().getId());
            jsonPlayer.addProperty("gameModeName", player.gameMode.getGameModeForPlayer().getName());
            jsonPlayer.addProperty("isInvisible", player.isInvisible());
            jsonPlayer.addProperty("experienceLevel", player.experienceLevel);
            jsonPlayers.add(player.getStringUUID(), jsonPlayer);
        }

        jsonResponse.add("players", jsonPlayers);

        return jsonResponse;
    }

    private static int armorPoints(final ServerPlayer player) {
        final @Nullable AttributeInstance attribute = player.getAttribute(Attributes.ARMOR);
        return attribute == null ? 0 : (int) attribute.getValue();
    }
}
