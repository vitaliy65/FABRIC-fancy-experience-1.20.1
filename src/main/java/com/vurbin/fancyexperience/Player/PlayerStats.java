package com.vurbin.fancyexperience.Player;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import static com.vurbin.fancyexperience.FancyExperience.MOD_ID;
import static com.vurbin.fancyexperience.FancyExperience.playerPersistentState;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Objects;

public class PlayerStats {
    public static final Identifier UPDATE_EXP_PACKET_ID = new Identifier(MOD_ID, "update_exp");
    public static final Identifier UPDATE_STATS_PACKET_ID = new Identifier(MOD_ID, "update_stats");


    // Опыт и уровни
    private int exp;
    private int currentLevel = 0;
    private int expForNextLevel = 10;
    private int vitality, endurance, strength, agility, luck;
    private float health = 20;
    private float defense = 0;
    private float attack = 2;

    // Конструктор
    public PlayerStats(int vitality, int endurance, int strength, int agility, int luck) {
        this.vitality = vitality;
        this.endurance = endurance;
        this.strength = strength;
        this.agility = agility;
        this.luck = luck;
    }

    public void increaseVitality(int amount) {
        if(exp - expForNextLevel > 0) {
            upLevel();
            vitality += amount;
            playerPersistentState.writeNbt ( writeToNbt ( ) );
        }
    }
    public void increaseEndurance(int amount) {
        if(exp - expForNextLevel > 0) {
            upLevel ();
            endurance += amount;
            playerPersistentState.writeNbt ( writeToNbt ( ) );
        }
    }
    public void increaseStrength(int amount) {
        if(exp - expForNextLevel > 0) {
            upLevel ();
            strength += amount;
            playerPersistentState.writeNbt ( writeToNbt ( ) );
        }
    }
    public void increaseAgility(int amount) {
        if(exp - expForNextLevel > 0) {
            upLevel ();
            agility += amount;
            playerPersistentState.writeNbt ( writeToNbt ( ) );
        }
    }
    public void increaseLuck(int amount) {
        if (exp-expForNextLevel > 0) {
            upLevel ();
            luck += amount;
            playerPersistentState.writeNbt ( writeToNbt ( ) );
        }
    }
    // Геттеры для получения текущих значений
    public int getVitality() { return vitality; }
    public int getEndurance() { return endurance; }
    public int getStrength() { return strength; }
    public int getAgility() { return agility; }
    public int getLuck() { return luck; }
    public int getCurrentLevel() {
        return currentLevel;
    }
    public int getExpForNextLevel() {
        return expForNextLevel;
    }
    public float getHealth() { return health; }
    public float getDefense() { return defense; }
    public float getAttack() { return attack; }
    public int getExp() { return exp; }

    public void setExp(int exp){
        this.exp = exp;
    }
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    // Методы работы с опытом и уровнем
    private void recalculateNextLevel() {
        // Линейная формула расчета опыта для следующего уровня
        expForNextLevel = (expForNextLevel + (int)(currentLevel * 1.2f));
    }

    public void upLevel() {
        PlayerEntity player = MinecraftClient.getInstance ().player;
        if (player != null) {
            // Сначала уменьшаем опыт локально
            player.addExperience(-expForNextLevel);

            // Отправляем данные об изменении опыта на сервер
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeInt(-expForNextLevel); // Уменьшение на expForNextLevel
            ClientPlayNetworking.send(UPDATE_EXP_PACKET_ID, buf);
        }

        currentLevel++;
        recalculateNextLevel();
        recalculateStats();
    }

    // Пересчёт производных характеристик
    private void recalculateStats() {
        PlayerEntity player = MinecraftClient.getInstance ().player;
        health = 20 + vitality * 0.7f + endurance * 0.7f; // Здоровье
        defense = 0 + endurance * 0.5f + agility * 0.5f; // Защита
        attack = 2 + strength * 0.15f + agility * 0.15f; // Атака

        if (player != null) {
            // Обновляем локально на клиенте
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(health);
            player.setHealth(player.getMaxHealth());
            Objects.requireNonNull ( player.getAttributeInstance ( EntityAttributes.GENERIC_ARMOR ) ).setBaseValue(defense);
            Objects.requireNonNull ( player.getAttributeInstance ( EntityAttributes.GENERIC_ATTACK_DAMAGE ) ).setBaseValue(attack);

            // Отправляем данные на сервер
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeFloat(health);
            buf.writeFloat(defense);
            buf.writeFloat(attack);
            ClientPlayNetworking.send(UPDATE_STATS_PACKET_ID, buf);
        }
    }

    public NbtCompound writeToNbt() {
        NbtCompound nbt = new NbtCompound ();
        nbt.putInt("exp", exp);
        nbt.putInt("currentLevel", currentLevel);
        nbt.putInt("expForNextLevel", expForNextLevel);
        nbt.putInt("vitality", vitality);
        nbt.putInt("endurance", endurance);
        nbt.putInt("strength", strength);
        nbt.putInt("agility", agility);
        nbt.putInt("luck", luck);

        nbt.putFloat("health", health);
        nbt.putFloat("defense", defense);
        nbt.putFloat("attack", attack);
        return nbt;
    }

    public void readFromNbt(NbtCompound nbt) {
        this.exp = nbt.getInt("exp");
        this.currentLevel = nbt.getInt("currentLevel");
        this.expForNextLevel = nbt.getInt("expForNextLevel");
        this.vitality = nbt.getInt("vitality");
        this.endurance = nbt.getInt("endurance");
        this.strength = nbt.getInt("strength");
        this.agility = nbt.getInt("agility");
        this.luck = nbt.getInt("luck");

        this.health = nbt.getFloat ("health");
        this.defense = nbt.getFloat("defense");
        this.attack = nbt.getFloat("attack");
    }

    public void set(PlayerStats stats) {
        this.vitality = stats.getVitality ();
        this.agility = stats.getAgility ();
        this.endurance = stats.getEndurance ();
        this.luck = stats.getLuck ();
        this.strength = stats.getStrength ();

        this.exp = stats.getExp ();
        this.currentLevel = stats.getCurrentLevel ();
        this.expForNextLevel = stats.getExpForNextLevel ();

        this.health = stats.getHealth ();
        this.defense = stats.getDefense ();
        this.attack = stats.getAttack ();
    }

}
