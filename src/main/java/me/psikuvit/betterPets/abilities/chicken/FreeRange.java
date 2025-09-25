package me.psikuvit.betterPets.abilities.chicken;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class FreeRange implements IAbility {
    @Override
    public void onEquip(Player paramPlayer) {

    }

    @Override
    public void onUnequip(Player paramPlayer) {

    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.FARMING_FORTUNE, 1, 1);
    }
}
