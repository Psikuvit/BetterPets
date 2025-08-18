package me.psikuvit.betterPets.abilities.parrot;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class Flamboyant implements IAbility {
    public void onEquip(Player owner) {
    }

    public void onUnequip(Player owner) {
    }

    public AbilityStat getAbilityStat() {
        return new AbilityStat(1.0D, 0.14D);
    }
}
