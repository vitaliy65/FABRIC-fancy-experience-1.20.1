package com.vurbin.fancyexperience.mixin;

import com.vurbin.fancyexperience.Player.BonusHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.vurbin.fancyexperience.Player.BonusHandler.playerStats;

@Mixin(PlayerEntity.class)
public abstract class PlayerDamageMixin extends LivingEntity {
    private final BonusHandler bonusHandler = new BonusHandler(playerStats);

    protected PlayerDamageMixin(EntityType<? extends LivingEntity> entityType , World world) {
        super ( entityType , world );
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void onPlayerDamage(DamageSource source , float amount , CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity player = MinecraftClient.getInstance ().player;

        if (player.isInvulnerableTo(source)) {
            cir.setReturnValue(false);
        } else if (player.getAbilities ().invulnerable && !source.isIn( DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            cir.setReturnValue(false);
        } else {
            if (player.isDead()) {
                cir.setReturnValue(false);
            } else {

                if (source.isScaledWithDifficulty()) {
                    if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                        amount = 0.0F;
                    }

                    if (player.getWorld().getDifficulty() == Difficulty.EASY) {
                        amount = Math.min(amount / 2.0F + 1.0F, amount);
                    }

                    if (player.getWorld().getDifficulty() == Difficulty.HARD) {
                        amount = amount * 3.0F / 2.0F;
                    }
                }
                float reducedDamage = getReducedDamage(source, amount);
                super.damage (  source, reducedDamage);
                cir.setReturnValue(true);
            }
        }
    }

    @Unique
    private float getReducedDamage(DamageSource source, float amount) {
        // Считаем уменьшение урона в зависимости от типа
        float reducedDamage = amount;
        if (source.getName().equals("inFire") || source.getName().equals("onFire")) {
            reducedDamage -= bonusHandler.calculateFireResistanceBonus(amount);
        } else if (source.getName().equals("magic")) {
            reducedDamage -= bonusHandler.calculatePoisonResistanceBonus(amount);
        } else if (source.getName().equals("arrow") || source.getName().equals("thrown") || source.getName().equals("mob")) {
            reducedDamage -= bonusHandler.calculatePhysicalResistanceBonus(amount);
        } else if (source.getName().equals("wither")) {
            reducedDamage -= bonusHandler.calculateWitherResistanceBonus(amount);
        }

        // Убедимся, что урон не становится отрицательным
        if (reducedDamage < 0) {
            reducedDamage = 0;
        }
        return reducedDamage;
    }
}