package me.psikuvit.betterPets.abilities.phoenix;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class FourthFlare implements IAbility {
    @Override
    public void onEquip(Player paramPlayer) {

    }

    @Override
    public void onUnequip(Player paramPlayer) {

    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(1.1, 0.14);
    }
}
