package com.vurbin.fancyexperience.Player;

public class BonusHandler {
    public static final float LUCK_MULTIPLIER = 0.05f; // 5% per point of Luck
    public static final float RESISTANCE_MULTIPLIER = 0.02f;

    public BonusHandler() {}

    // Experience Bonus Calculation
    public static int calculateExperienceBonus(int baseExperience, int stat) {
        return Math.round(baseExperience * stat * LUCK_MULTIPLIER);
    }

    // Generalized Resistance Calculation
    public static float calculateResistanceBonus(float baseDamage, float statValue) {
        float resistance = statValue * RESISTANCE_MULTIPLIER;
        return (baseDamage * resistance);
    }
}
