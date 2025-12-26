package com.dasred.minecraft_api.chunk;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ChunkTracker {

    private static final Map<ResourceKey<Level>, Set<ChunkPos>> LOADED = new ConcurrentHashMap<>();

    public static Set<ChunkPos> getLoaded(ServerLevel level) {
        return LOADED.getOrDefault(level.dimension(), Set.of());
    }

    static void add(ServerLevel level, ChunkPos pos) {
        LOADED.computeIfAbsent(level.dimension(), k -> ConcurrentHashMap.newKeySet())
                .add(pos);
    }

    static void remove(ServerLevel level, ChunkPos pos) {
        Set<ChunkPos> set = LOADED.get(level.dimension());
        if (set != null) {
            set.remove(pos);
        }
    }
}