package me.psikuvit.betterPets.abilities.flying_fish;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class RapidDecay implements IAbility {

    @Override
    public void onEquip(Player owner) {
        // Event-based ability - no equip action needed
    }

    @Override
    public void onUnequip(Player owner) {
        // Event-based ability - no unequip action needed
    }

    @Override
    public AbilityStats getAbilityStat() {
        return null;
    }
}
