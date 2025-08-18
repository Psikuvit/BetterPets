package me.psikuvit.betterPets.abilities.wolf;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class PackLeader implements IAbility {
    @Override
    public void onEquip(Player owner) {
    }

    @Override
    public void onUnequip(Player owner) {
    }

    

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}
