package me.psikuvit.betterPets.abilities.guardian;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class LaserBeam implements IAbility {
    @Override
    public void onEquip(Player owner) {
        // Offensive ability handled via events; placeholder
    }

    @Override
    public void onUnequip(Player owner) {
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}
