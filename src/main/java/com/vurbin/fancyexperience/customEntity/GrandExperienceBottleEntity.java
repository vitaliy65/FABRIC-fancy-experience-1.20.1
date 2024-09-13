package com.vurbin.fancyexperience.customEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class GrandExperienceBottleEntity extends ThrownItemEntity {
    public GrandExperienceBottleEntity(EntityType<? extends ExperienceBottleEntity> entityType, World world) {
        super(entityType, world);
    }

    public GrandExperienceBottleEntity(World world, LivingEntity owner) {
        super(EntityType.EXPERIENCE_BOTTLE, owner, world);
    }

    public GrandExperienceBottleEntity(World world, double x, double y, double z) {
        super(EntityType.EXPERIENCE_BOTTLE, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EXPERIENCE_BOTTLE;
    }

    @Override
    protected float getGravity() {
        return 0.07F;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.getWorld() instanceof ServerWorld) {
            this.getWorld().syncWorldEvent(WorldEvents.SPLASH_POTION_SPLASHED, this.getBlockPos(), PotionUtil.getColor(Potions.WATER));
            int i = 15 + this.getWorld().random.nextInt(30) + this.getWorld().random.nextInt(30);
            ExperienceOrbEntity.spawn((ServerWorld)this.getWorld(), this.getPos(), i);
            this.discard();
        }
    }
}