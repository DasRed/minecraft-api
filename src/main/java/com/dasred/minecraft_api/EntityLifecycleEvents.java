package com.dasred.minecraft_api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber
public class EntityLifecycleEvents {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (event.getLevel().isClientSide()) {
            return;
        }

        boolean update = false;
        CompoundTag root = entity.getPersistentData();

        CompoundTag api = root.getCompoundOrEmpty("minecraft_api");
        if (api.getInt("version").isEmpty()) {
            api.putLong("version", 1);
            update = true;
        }

        CompoundTag born = api.getCompoundOrEmpty("born");
        if (born.contains("timestamp") == false) {
            born.putLong("timestamp", System.currentTimeMillis());
            update = true;
        }

        if (born.contains("gameTime") == false) {
            born.putLong("gameTime", event.getLevel().getGameTime());
            update = true;
        }

        if (update == true) {
            api.put("born", born);
            root.put("minecraft_api", api);
        }
    }
}