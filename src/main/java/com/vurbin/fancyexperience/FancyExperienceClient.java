package com.vurbin.fancyexperience;

import com.vurbin.fancyexperience.Player.BonusHandler;
import com.vurbin.fancyexperience.Screen.ExperienceCounter;
import com.vurbin.fancyexperience.Screen.KeybindingHandler;
import com.vurbin.fancyexperience.customParticles.ExperienceCustomParticle;
import com.vurbin.fancyexperience.customRenderer.CustomExperienceOrbRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.entity.EntityType;

import static com.vurbin.fancyexperience.Player.BonusHandler.playerStats;

public class FancyExperienceClient implements ClientModInitializer {
    KeybindingHandler keyHandler;
    BonusHandler experienceBonusHandler;
    @Override
    public void onInitializeClient() {
        // Отключаем стандартный рендер и регистрируем кастомный рендер для ExperienceOrbEntity
        EntityRendererRegistry.register(EntityType.EXPERIENCE_ORB, CustomExperienceOrbRenderer::new);

        HudRenderCallback.EVENT.register(new ExperienceCounter(playerStats));

        keyHandler = new KeybindingHandler( playerStats );

        experienceBonusHandler = new BonusHandler (playerStats);

        ExperienceCustomParticle.registerClient ();
    }
}