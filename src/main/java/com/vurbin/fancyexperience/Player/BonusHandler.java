package com.vurbin.fancyexperience.Player;

public class BonusHandler {
    public static PlayerStats playerStats = null;
    public static final float LUCK_MULTIPLIER = 0.05f; // 5% per point of Luck
    public static final float RESISTANCE_MULTIPLIER = 0.02f;

    public BonusHandler(PlayerStats playerStats) {
        BonusHandler.playerStats = playerStats;
    }

    // Experience Bonus Calculation
    public int calculateExperienceBonus(int baseExperience) {
        float luck = playerStats.getLuck();
        return Math.round(baseExperience * luck * LUCK_MULTIPLIER);
    }

    // Generalized Resistance Calculation
    private float calculateResistanceBonus(float baseDamage, float statValue) {
        float resistance = statValue * RESISTANCE_MULTIPLIER;
        return (baseDamage * resistance);
    }

    // Resistance Calculations
    public float calculatePhysicalResistanceBonus(float baseDamage) {
        return calculateResistanceBonus(baseDamage, playerStats.getVitality());
    }

    public float calculatePoisonResistanceBonus(float baseDamage) {
        return calculateResistanceBonus(baseDamage, playerStats.getEndurance());
    }

    public float calculateFireResistanceBonus(float baseDamage) {
        return calculateResistanceBonus(baseDamage, playerStats.getStrength());
    }

    public float calculateWitherResistanceBonus(float baseDamage) {
        return calculateResistanceBonus(baseDamage, playerStats.getAgility());
    }
}
