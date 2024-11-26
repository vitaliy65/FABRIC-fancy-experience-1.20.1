package com.vurbin.fancyexperience.customParticles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.vurbin.fancyexperience.FancyExperience.MOD_ID;

public class ExperienceCustomParticle extends AnimatedParticle {
    public  static final DefaultParticleType EXPERIENCE_GREEN_TRAIL = FabricParticleTypes.simple();

    public ExperienceCustomParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale *= 0.3F;
        this.maxAge = 60 + this.random.nextInt(12);
        this.setTargetColor(15916745);
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ExperienceCustomParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }


    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of ( MOD_ID , "experience_particle" ) , EXPERIENCE_GREEN_TRAIL);
    }


    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(EXPERIENCE_GREEN_TRAIL, ExperienceCustomParticle.Factory::new);
    }
}
