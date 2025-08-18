package me.psikuvit.betterPets.abilities;

import me.psikuvit.betterPets.utils.Messages;

public record AbilityStat(double baseValue, double perLevelIncrease) {

    /**
     * Constructor that rounds values to avoid floating-point precision issues
     */
    public AbilityStat(double baseValue, double perLevelIncrease) {
        this.baseValue = Messages.round(baseValue);
        this.perLevelIncrease = Messages.round(perLevelIncrease);
    }

    /**
     * Calculate the stat value for a given level with proper rounding
     */
    public double getValueAtLevel(int level) {
        return Messages.round(baseValue + (perLevelIncrease * (level - 1)));
    }
}

