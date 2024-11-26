package com.vurbin.fancyexperience.mixin;

import com.vurbin.fancyexperience.Player.ExperienceBonusHandler;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbMixin {

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ExperienceOrbEntity orb = (ExperienceOrbEntity) (Object) this; // Преобразуем контекст
            int baseExperience = orb.getExperienceAmount(); // Получаем базовый опыт орба

            // Получаем PlayerStats для игрока
            var playerStats = ExperienceBonusHandler.playerStats;
            if (playerStats != null) {
                // Вычисляем бонусный опыт
                ExperienceBonusHandler bonusHandler = new ExperienceBonusHandler(playerStats);
                int bonusExperience = bonusHandler.calculateBonus(baseExperience);

                // Добавляем опыт игроку вручную
                serverPlayer.addExperience(baseExperience + bonusExperience);

                // Отменяем стандартное поведение
                orb.discard(); // Удаляем орб из мира
                ci.cancel();
            }
        }
    }
}
