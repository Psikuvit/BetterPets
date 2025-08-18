package me.psikuvit.betterPets.abilities.bee;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BusyBuzz implements IAbility {
    @Override
    public void onEquip(Player owner) {
        owner.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 20 * 30, 1, true, false));
    }

    @Override
    public void onUnequip(Player owner) {
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}
