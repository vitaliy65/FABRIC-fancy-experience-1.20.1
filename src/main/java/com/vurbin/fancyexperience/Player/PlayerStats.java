package com.vurbin.fancyexperience.Player;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import static com.vurbin.fancyexperience.FancyExperience.MOD_ID;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Objects;

public class PlayerStats {
    public static final Identifier UPDATE_EXP_PACKET_ID = new Identifier(MOD_ID, "update_exp");
    public static final Identifier UPDATE_STATS_PACKET_ID = new Identifier(MOD_ID, "update_stats");


    // Опыт и уровни
    private int exp;                // Текущий опыт
    private int currentLevel = 0;   // Текущий уровень
    private int expForNextLevel = 10; // Опыт для следующего уровня

    // Характеристики персонажа
    private int vitality, endurance, strength, agility, luck;    // Живучесть
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
        }
    }
    public void increaseEndurance(int amount) {
        if(exp - expForNextLevel > 0) {
            upLevel ();
            endurance += amount;
        }
    }
    public void increaseStrength(int amount) {
        if(exp - expForNextLevel > 0) {
            upLevel ();
            strength += amount;
        }
    }
    public void increaseAgility(int amount) {
        if(exp - expForNextLevel > 0) {
            upLevel ();
            agility += amount;
        }
    }
    public void increaseLuck(int amount) {
        if (exp-expForNextLevel > 0) {
            upLevel ();
            luck += amount;
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

    // Методы работы с опытом и уровнем
    private void recalculateNextLevel() {
        // Линейная формула расчета опыта для следующего уровня
        expForNextLevel = (expForNextLevel + (int)(currentLevel * 1.2f));
    }

    public void upLevel() {
        var player = MinecraftClient.getInstance ().player;
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
        var player = MinecraftClient.getInstance ().player;
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
}
