package me.psikuvit.betterPets.abilities.elephant;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;

public class Stomp implements IAbility {
    @Override
    public void onEquip(Player owner) {
        // Area damage logic would be implemented via events; placeholder for now.
    }

    @Override
    public void onUnequip(Player owner) {
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}
