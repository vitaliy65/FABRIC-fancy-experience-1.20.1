package com.vurbin.fancyexperience.customRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ExperienceOrbEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static com.vurbin.fancyexperience.FancyExperience.MOD_ID;

@Environment(EnvType.CLIENT)
public class CustomExperienceOrbRenderer extends ExperienceOrbEntityRenderer {

    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/experience_orb/experience_orb.png");

    public CustomExperienceOrbRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0;
        this.shadowOpacity = 0;
    }

    @Override
    public void render(ExperienceOrbEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Получаем потребителя вершин для рендеринга с пользовательской текстурой
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));

        // Получаем трансформационные матрицы (позиционная матрица и нормальная матрица)
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();

        // Используем фиксированные нормали для объекта (например, нормаль, направленную вверх)
        Matrix3f normalMatrix = new Matrix3f(); // создаем новую нормаль
        normalMatrix.identity(); // устанавливаем нормаль в "положительное направление Y", чтобы избежать изменений при вращении

        // Определяем координаты текстуры
        float u1 = 0.0F;
        float v1 = 0.0F;
        float u2 = 1.0F;
        float v2 = 1.0F;
        float rotation = ((float)entity.age + tickDelta) * 5;

        matrices.scale(0.25F, 0.25F, 0.25F);
        matrices.translate(0.0F, 1F, 0.0F);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));

        // Добавляем вершины для рендеринга квадрата
        this.vertex(vertexConsumer, positionMatrix, normalMatrix, -0.5F, -0.5F, 255, 255, 255, u1, v2, light);
        this.vertex(vertexConsumer, positionMatrix, normalMatrix, 0.5F, -0.5F, 255, 255, 255, u2, v2, light);
        this.vertex(vertexConsumer, positionMatrix, normalMatrix, 0.5F, 0.5F, 255, 255, 255, u2, v1, light);
        this.vertex(vertexConsumer, positionMatrix, normalMatrix, -0.5F, 0.5F, 255, 255, 255, u1, v1, light);
    }


    private void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, int red, int green, int blue, float u, float v, int light) {
        // Apply emissive color for the glowing effect
        vertexConsumer.vertex(positionMatrix, x, y, 0.0F)
                .color(red, green, blue, 255)  // Full opacity (255)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .next();
    }

    @Override
    public Identifier getTexture(ExperienceOrbEntity entity) {
        // Always return the custom texture for the experience orb
        return TEXTURE;
    }
}
