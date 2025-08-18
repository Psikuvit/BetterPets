package me.psikuvit.betterPets.pet;

import com.willfp.ecoskills.stats.Stat;
import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.enums.Stats;

public class PetAttribute {

    private double baseValue;
    private final double perLevelIncrease;
    private final Stats stat;

    public PetAttribute(double baseValue, double perLevelIncrease, Stats stat) {
        this.baseValue = Messages.round(baseValue);
        this.perLevelIncrease = Messages.round(perLevelIncrease);
        this.stat = stat;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public double getValue(int level) {
        return Messages.round(baseValue + (perLevelIncrease * (level - 1)));
    }

    public void addBoost(double amount) {
        baseValue = Messages.round(baseValue + amount);
    }

    public void removeBoost(double amount) {
        baseValue = Messages.round(baseValue - amount);
    }

    public Stat getStat() {
        return stat.getStat();
    }
}
