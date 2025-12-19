package com.dasred.minecraft_api;

import com.dasred.minecraft_api.api.HttpApiServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MinecraftAPI.MODID)
public class MinecraftAPI {
    public static final String MODID = "minecraft_api";

    public MinecraftAPI(IEventBus modBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);

        // Start server at MC start
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.server.ServerStartedEvent e) -> {
            HttpApiServer.start(e.getServer(), Config.PORT.getAsInt());
        });

        // Stop server when MC stops
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.server.ServerStoppingEvent e) -> {
            HttpApiServer.stop();
        });
    }
}