package me.psikuvit.betterPets.abilities.grandma_wolf;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class KillCombo implements IAbility {

    @Override
    public void onEquip(Player paramPlayer) {

    }

    @Override
    public void onUnequip(Player paramPlayer) {

    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(8, 0.02);
    }
}
