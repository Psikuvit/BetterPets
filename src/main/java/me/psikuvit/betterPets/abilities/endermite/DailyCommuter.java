package me.psikuvit.betterPets.abilities.endermite;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class DailyCommuter implements IAbility {

    @Override
    public void onEquip(Player owner) {
    }

    @Override
    public void onUnequip(Player owner) {
    }


    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.4, 0.4);
    }
}

