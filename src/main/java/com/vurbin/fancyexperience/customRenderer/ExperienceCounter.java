package com.vurbin.fancyexperience.customRenderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.vurbin.fancyexperience.Player.PlayerStats;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;

public class ExperienceCounter implements HudRenderCallback {
    PlayerStats _playerStats;
    MinecraftClient client = MinecraftClient.getInstance();
    public ExperienceCounter(PlayerStats playerStats) {
        _playerStats = playerStats;
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        // Проверка, что игрок существует
        if (client.player != null) {
            int experienceLevel = client.player.experienceLevel;
            float experienceProgress = client.player.experienceProgress;

            int totalExperience = getTotalExperienceForLevel(experienceLevel) + (int)(experienceProgress * getNextExperienceCost(experienceLevel));
            _playerStats.setExp(totalExperience);

            // Формируем строку текста для отображения опыта
            String experienceText = "XP: " + totalExperience;

            // Получаем размеры экрана
            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();

            // Получаем текстовый рендерер
            TextRenderer textRenderer = client.textRenderer;

            // Вычисляем позицию для рендеринга текста (например, чуть ниже полоски опыта)
            int x = screenWidth / 2 - textRenderer.getWidth(experienceText) / 2;
            int y = screenHeight - 50; // Позиция чуть ниже полоски опыта

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1,1,1,1);

            drawContext.drawText(client.textRenderer,
                                 experienceText,
                                 x, y,
                                 0xFFFFFF,
                                 true);
        }
    }

    public int getTotalExperienceForLevel(int level) {
        if (level == 0) return 0;
        if (level <= 16) return level * level + 6 * level;
        if (level <= 31) return (int) (2.5 * level * level - 40.5 * level + 360);
        return (int) (4.5 * level * level - 162.5 * level + 2220);
    }

    public int getNextExperienceCost(int level) {
        if (level <= 15) return 2 * level + 7;
        if (level <= 30) return 5 * level - 38;
        return 9 * level - 158;
    }
}
