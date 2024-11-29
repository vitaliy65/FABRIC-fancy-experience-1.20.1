package com.vurbin.fancyexperience.Player;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.vurbin.fancyexperience.FancyExperience.*;

public class PlayerPersistentState extends PersistentState {
    // Потокобезопасная карта для хранения состояния игроков
    private final Map<UUID, PlayerStats> playerStatsMap = new ConcurrentHashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        playerStatsMap.forEach((uuid, stats) -> nbt.put(uuid.toString(), stats.writeToNbt()));
        return nbt;
    }

    public static PlayerPersistentState createFromNbt(NbtCompound nbt) {
        PlayerPersistentState state = new PlayerPersistentState();
        nbt.getKeys().forEach(key -> {
            try {
                UUID uuid = UUID.fromString(key);
                PlayerStats stats = new PlayerStats(0, 0, 0, 0, 0);
                stats.readFromNbt(nbt.getCompound(key));
                state.playerStatsMap.put(uuid, stats);
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Ошибка при загрузке данных для UUID: " + key, e);
            }
        });
        return state;
    }

    public static PlayerPersistentState getServerState(MinecraftServer server) {
        return server.getWorld(World.OVERWORLD)
                .getPersistentStateManager()
                .getOrCreate(PlayerPersistentState::createFromNbt, PlayerPersistentState::new, "fancyexperience");
    }

    public PlayerStats getPlayerState(UUID uuid) {
        return playerStatsMap.computeIfAbsent(uuid, key -> new PlayerStats(0, 0, 0, 0, 0));
    }

    public void savePlayerState(UUID uuid, PlayerStats stats) {
        playerStatsMap.put(uuid, stats);
        this.markDirty();
    }

    public static void registerPlayerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            PlayerPersistentState serverState = getServerState(server);

            // Загружаем данные игрока в globalStats
            loadPlayerStatsToGlobal(player.getUuid(), server);
            LOGGER.info("Setting player data: " + player.getName().getString() + "    " + player.getUuid ());
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            PlayerPersistentState serverState = getServerState(server);
            serverState.savePlayerState(player.getUuid(), _stats);
            LOGGER.info("Saving player data: " + player.getName().getString() + "    " + player.getUuid ());
        });
    }
}
