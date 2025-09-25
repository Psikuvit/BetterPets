package me.psikuvit.betterPets.abilities.giraffe;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class GoodHeart implements IAbility {

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
        return new AbilityStats().addStatAmplifier(Stats.HEALTH, 0.5, 0.5);
    }
}
