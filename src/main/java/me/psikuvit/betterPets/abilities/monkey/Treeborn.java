package me.psikuvit.betterPets.abilities.monkey;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class Treeborn implements IAbility {

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
        return new AbilityStats().addStatAmplifier(Stats.FORAGING_FORTUNE, 0.6, 0.6);
    }
}
