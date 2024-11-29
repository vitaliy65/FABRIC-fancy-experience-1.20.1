package com.vurbin.fancyexperience;

import com.vurbin.fancyexperience.Player.PlayerPersistentState;
import com.vurbin.fancyexperience.Player.PlayerStats;
import com.vurbin.fancyexperience.customParticles.ExperienceCustomParticle;
import com.vurbin.fancyexperience.item.ModItemGroups;
import com.vurbin.fancyexperience.item.ModItems;
import com.vurbin.fancyexperience.particlesEngine.ExperienceOrbParticleSpawner;
import com.vurbin.fancyexperience.server.ServerNetworkingHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FancyExperience implements ModInitializer {
	public static final String MOD_ID = "fancyexperience";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static PlayerPersistentState playerPersistentState;
	public static PlayerStats _stats;

	@Override
	public void onInitialize() {
		// Инициализация глобальных состояний
		playerPersistentState = new PlayerPersistentState();
		_stats = new PlayerStats(0, 0, 0, 0, 0);

		// Регистрация предметов
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		// Регистрация серверных обработчиков
		ServerNetworkingHandler.register();

		// Регистрация кастомных частиц
		ExperienceOrbParticleSpawner.registerParticlesSpawning();
		ExperienceCustomParticle.register();

		// Регистрация событий игрока
		PlayerPersistentState.registerPlayerEvents();

		LOGGER.info("FancyExperience мод успешно инициализирован");
	}

	// Метод для загрузки данных игрока в globalStats
	public static void loadPlayerStatsToGlobal(UUID playerUUID, MinecraftServer server) {
		PlayerPersistentState state = PlayerPersistentState.getServerState(server);
		PlayerStats savedStats = state.getPlayerState(playerUUID);

		if (savedStats != null) {
			_stats.set(savedStats); // Копируем значения из сохраненных данных
			LOGGER.info("Данные игрока загружены в глобальные переменные");
		} else {
			LOGGER.warn("Не удалось загрузить данные игрока. Используются значения по умолчанию.");
		}
	}
}
