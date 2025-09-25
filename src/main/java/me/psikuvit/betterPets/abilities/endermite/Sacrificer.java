package me.psikuvit.betterPets.abilities.endermite;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class Sacrificer implements IAbility {

    @Override
    public void onEquip(Player owner) {
    }

    @Override
    public void onUnequip(Player owner) {
        // Remove any applied bonus odds
    }

    @Override
    public AbilityStats getAbilityStat() {
        return null;
    }
}
