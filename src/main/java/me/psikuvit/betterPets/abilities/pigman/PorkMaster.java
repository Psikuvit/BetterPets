package me.psikuvit.betterPets.abilities.pigman;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class PorkMaster implements IAbility {
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
        return new AbilityStats()
                .addStatAmplifier(Stats.ABILITY_DAMAGE, 0.5, 0.5)
                .addStatAmplifier(Stats.STRENGTH, 0.5, 0.5);
    }
}
