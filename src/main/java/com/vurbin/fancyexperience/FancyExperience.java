package com.vurbin.fancyexperience;

import com.vurbin.fancyexperience.customParticles.ExperienceCustomParticle;
import com.vurbin.fancyexperience.item.ModItemGroups;
import com.vurbin.fancyexperience.item.ModItems;
import com.vurbin.fancyexperience.particlesEngine.ExperienceOrbParticleSpawner;
import com.vurbin.fancyexperience.server.ServerNetworkingHandler;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FancyExperience implements ModInitializer {
	public static final String MOD_ID = "fancyexperience";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ServerNetworkingHandler.register();
		ExperienceOrbParticleSpawner.registerParticlesSpawning();
		ExperienceCustomParticle.register();
	}
}