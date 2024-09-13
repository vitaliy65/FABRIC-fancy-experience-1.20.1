package com.vurbin.fancyexperience.particlesEngine;

import com.vurbin.fancyexperience.FancyExperience;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;

import java.util.List;

public class ExperienceOrbParticleSpawner {
    public static void registerParticlesSpawning() {
        FancyExperience.LOGGER.info("Initializing ExperienceOrbParticleSpawner...");

        // Реєструємо подію клієнтського тіку
        ClientTickEvents.END_WORLD_TICK.register(ExperienceOrbParticleSpawner::onWorldTick);
    }

    private static void onWorldTick(ClientWorld clientWorld) {
        if (clientWorld == null) {
            FancyExperience.LOGGER.warn("ClientWorld is null, skipping tick event.");
            return;
        }

        // Отримуємо список гравців у клієнтському світі
        List<AbstractClientPlayerEntity> players = clientWorld.getPlayers();

        // Перевіряємо, чи є хоча б один гравець
        if (players.isEmpty()) {
            FancyExperience.LOGGER.warn("No players found in the world.");
            return;
        }

        // Використовуємо першого гравця для орієнтації по координатах
        ClientPlayerEntity player = (ClientPlayerEntity) players.get(0);

        // Окреслюємо діапазон, в якому будемо шукати досвідні сфери (обмежений радіус)
        double range = 50.0;
        Box worldBox = new Box(
                player.getX() - range, player.getY() - range, player.getZ() - range,
                player.getX() + range, player.getY() + range, player.getZ() + range);

        // Отримуємо всі досвідні сфери в заданому діапазоні
        List<ExperienceOrbEntity> experienceOrbs = clientWorld.getEntitiesByClass(ExperienceOrbEntity.class, worldBox, entity -> true);

        //FancyExperience.LOGGER.info("Found {} experience orbs in range.", experienceOrbs.size());

        // Ітеруємо по знайдених досвідних сферах і створюємо частинки
        for (ExperienceOrbEntity orb : experienceOrbs) {
            spawnParticles(orb, clientWorld);
        }
    }

    private static void spawnParticles(ExperienceOrbEntity orb, ClientWorld clientWorld) {
        // Отримуємо позицію досвідної сфери
        double x = orb.getX();
        double y = orb.getY();
        double z = orb.getZ();

        // Лог для підтвердження спавнінгу частинок
        //FancyExperience.LOGGER.debug("Spawning particle at {}, {}, {}", x, y, z);

        // Спавнимо частинки на клієнті
        clientWorld.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y+0.2, z, 0.0, 0.0, 0.0);
    }
}
