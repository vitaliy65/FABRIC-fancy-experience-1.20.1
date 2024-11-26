package com.vurbin.fancyexperience.Player;

public class ExperienceBonusHandler {
    public static PlayerStats playerStats = null;

    public ExperienceBonusHandler(PlayerStats playerStats) {
        ExperienceBonusHandler.playerStats = playerStats;
    }

    public int calculateBonus(int baseExperience) {
        int luck = playerStats.getLuck();
        float luckMultiplier = 1 + (luck * 0.05f); // 5% за каждое очко Luck
        return Math.round(baseExperience * (luckMultiplier - 1));
    }
}
