package me.psikuvit.betterPets.abilities.wolf;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class CombatWisdom implements IAbility {
    @Override
    public void onEquip(Player paramPlayer) {
        applyAbilityStats(paramPlayer);
    }

    @Override
    public void onUnequip(Player paramPlayer) {
        removeAbilityStats(paramPlayer);
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.COMBAT_WISDOM, 0.3, 0.3);
    }
}
