package com.vurbin.fancyexperience;

import com.vurbin.fancyexperience.Player.ExperienceBonusHandler;
import com.vurbin.fancyexperience.Player.PlayerStats;
import com.vurbin.fancyexperience.Screen.KeybindingHandler;
import com.vurbin.fancyexperience.customParticles.ExperienceCustomParticle;
import com.vurbin.fancyexperience.customRenderer.CustomExperienceOrbRenderer;
import com.vurbin.fancyexperience.Screen.ExperienceCounter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.entity.EntityType;

public class FancyExperienceClient implements ClientModInitializer {
    public static PlayerStats _playerStats = new PlayerStats ( 0 , 0 , 0 , 0 , 0 );
    KeybindingHandler keyHandler;
    ExperienceBonusHandler experienceBonusHandler;
    @Override
    public void onInitializeClient() {
        // Отключаем стандартный рендер и регистрируем кастомный рендер для ExperienceOrbEntity
        EntityRendererRegistry.register(EntityType.EXPERIENCE_ORB, CustomExperienceOrbRenderer::new);
        HudRenderCallback.EVENT.register(new ExperienceCounter(_playerStats));
        keyHandler = new KeybindingHandler( _playerStats );
        experienceBonusHandler = new ExperienceBonusHandler(_playerStats);
        ExperienceCustomParticle.registerClient ();
    }
}