package me.psikuvit.betterPets.abilities.ocelot;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class TreeHugger implements IAbility {
    @Override
    public void onEquip(Player owner) {
    }

    @Override
    public void onUnequip(Player owner) {
    }

    

    @Override
    public AbilityStat getAbilityStat() {
        // Passive display stat only; values can be tuned later
        return new AbilityStat(0.0D, 0.0D);
    }
}
