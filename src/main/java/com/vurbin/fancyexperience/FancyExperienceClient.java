package com.vurbin.fancyexperience;

import com.vurbin.fancyexperience.Screen.ExperienceCounter;
import com.vurbin.fancyexperience.Screen.KeybindingHandler;
import com.vurbin.fancyexperience.customParticles.ExperienceCustomParticle;
import com.vurbin.fancyexperience.customRenderer.CustomExperienceOrbRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.entity.EntityType;

public class FancyExperienceClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Регистрация кастомного рендера для ExperienceOrbEntity
        EntityRendererRegistry.register(EntityType.EXPERIENCE_ORB, CustomExperienceOrbRenderer::new);

        // Регистрация HUD-обработчика
        HudRenderCallback.EVENT.register(new ExperienceCounter());

        // Регистрация обработчика клавиш
        KeybindingHandler.initializeKeyStates();

        // Регистрация клиентских частиц
        ExperienceCustomParticle.registerClient();
    }
}
