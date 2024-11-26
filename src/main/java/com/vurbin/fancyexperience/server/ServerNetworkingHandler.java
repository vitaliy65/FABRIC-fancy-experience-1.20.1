package com.vurbin.fancyexperience.server;

import com.vurbin.fancyexperience.Player.PlayerStats;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.Objects;

public class ServerNetworkingHandler {
    public static void register() {
        // Обработчик изменения опыта
        ServerPlayNetworking.registerGlobalReceiver(PlayerStats.UPDATE_EXP_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int expChange = buf.readInt();

            // Обновляем опыт на стороне сервера
            server.execute(() -> {
                if (player != null) {
                    player.addExperience(expChange); // Применяем изменение опыта
                }
            });
        });

        // Обработчик изменения характеристик
        ServerPlayNetworking.registerGlobalReceiver(PlayerStats.UPDATE_STATS_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            float health = buf.readFloat();
            float defense = buf.readFloat();
            float attack = buf.readFloat();

            // Применение изменений на сервере
            server.execute(() -> {
                if (player != null) {
                    // Обновляем атрибуты на сервере
                    Objects.requireNonNull ( player.getAttributeInstance ( EntityAttributes.GENERIC_MAX_HEALTH ) ).setBaseValue(health);
                    player.setHealth(health);

                    // Обновление защиты
                    Objects.requireNonNull ( player.getAttributeInstance ( EntityAttributes.GENERIC_ARMOR ) ).setBaseValue(defense);

                    // Обновление атаки
                    Objects.requireNonNull ( player.getAttributeInstance ( EntityAttributes.GENERIC_ATTACK_DAMAGE ) ).setBaseValue(attack);

                    // Здесь также можно добавить обновления для других кастомных атрибутов, если они есть.
                }
            });
        });
    }
}
