package me.psikuvit.betterPets.abilities.monkey;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class VineSwing implements IAbility {

    @Override
    public void onEquip(Player owner) {
        applyAbilityStats(owner);
    }

    @Override
    public void onUnequip(Player owner) {
        removeAbilityStats(owner);
    }

    private boolean isInThePark(Player player) {
        return player.getWorld().getName().equalsIgnoreCase("park") ||
               player.getWorld().getName().toLowerCase().contains("park");
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.SPEED, 1, 1);
    }
}
