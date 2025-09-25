package me.psikuvit.betterPets.abilities.flying_fish;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class QuickReel implements IAbility {

    @Override
    public void onEquip(Player owner) {
        applyAbilityStats(owner);
    }

    @Override
    public void onUnequip(Player owner) {
        removeAbilityStats(owner);
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.FISHING_SPEED, 0.8, 0.8);
    }
}
