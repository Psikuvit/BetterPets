package me.psikuvit.betterPets.abilities.horse;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class RideIntoBattle implements IAbility {
    @Override
    public void onEquip(Player paramPlayer) {

    }

    @Override
    public void onUnequip(Player paramPlayer) {

    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.2, 0.25);
    }
}
