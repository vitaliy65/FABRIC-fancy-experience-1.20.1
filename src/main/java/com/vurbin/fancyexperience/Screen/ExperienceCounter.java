package com.vurbin.fancyexperience.Screen;

import com.vurbin.fancyexperience.Player.PlayerStats;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import oshi.jna.platform.windows.NtDll;

import static com.vurbin.fancyexperience.FancyExperience.MOD_ID;
import static com.vurbin.fancyexperience.FancyExperience._stats;

public class ExperienceCounter implements HudRenderCallback {
    private static final String COUNTER_BACKGROUND_TEXTURE = "textures/gui/exp_counter_background.png";
    Identifier backgroundTexture;
    MinecraftClient client = MinecraftClient.getInstance();
    public ExperienceCounter() {
        backgroundTexture = Identifier.of(MOD_ID, COUNTER_BACKGROUND_TEXTURE);
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        // Проверка, что игрок существует
        if (client.player != null) {
            int experienceLevel = client.player.experienceLevel;
            float experienceProgress = client.player.experienceProgress;

            int totalExperience = getTotalExperienceForLevel(experienceLevel) + (int)(experienceProgress * getNextExperienceCost(experienceLevel));
            _stats.setExp(totalExperience);

            int xText = drawContext.getScaledWindowWidth () - 29;
            int yText = drawContext.getScaledWindowHeight () - 92;
            RenderText(drawContext, String.valueOf ( totalExperience ), xText, yText);

            int xTextLevel = drawContext.getScaledWindowWidth () - 28;
            int yTextLevel = drawContext.getScaledWindowHeight () - 74;
            RenderText(drawContext, "Lvl " + experienceLevel, xTextLevel, yTextLevel);

            int xBackgroundPosition = drawContext.getScaledWindowWidth () - 75;
            int yBackgroundPosition = drawContext.getScaledWindowHeight () - 98;
            RenderBackground (drawContext, xBackgroundPosition,yBackgroundPosition,75,38, backgroundTexture);
        }
    }

    public void RenderText(DrawContext drawContext, String text, int x, int y){
        // Получаем текстовый рендерер
        TextRenderer textRenderer = client.textRenderer;

        drawContext.drawText(textRenderer,
                text,
                x - textRenderer.getWidth(text) / 2,
                y,
                0xFFFFFF,
                true);
    }

    public void RenderBackground(DrawContext context, int x, int y, int width, int height, Identifier backgroundTexture) {
        // Отрисовка фона
        context.drawTexture(backgroundTexture, x, y, 0, 0, width, height, width, height);
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
