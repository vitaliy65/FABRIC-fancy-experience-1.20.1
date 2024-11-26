package com.vurbin.fancyexperience.customRenderer;

import com.vurbin.fancyexperience.FancyExperience;
import net.minecraft.client.render.entity.ExperienceOrbEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.Identifier;

public class CustomExperienceOrbRenderer extends ExperienceOrbEntityRenderer {
    private static final Identifier CUSTOM_TEXTURE = new Identifier(FancyExperience.MOD_ID, "textures/entity/experience_orb.png");

    public CustomExperienceOrbRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ExperienceOrbEntity entity) {
        return CUSTOM_TEXTURE;
    }
}
