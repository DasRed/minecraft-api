package com.dasred.minecraft_api;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue PORT;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("api");

        PORT = builder
                .comment("Defines the port for the api")
                .defineInRange("port", 9874, 1, 65535);

        builder.pop();

        SPEC = builder.build();
    }
}