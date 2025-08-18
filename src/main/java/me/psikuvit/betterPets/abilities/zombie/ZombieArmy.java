package me.psikuvit.betterPets.abilities.zombie;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieArmy implements IAbility {
    @Override
    public void onEquip(Player owner) {
        owner.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 60, 2, true, false));
        owner.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 90, 1, true, false));
        owner.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 45, 1, true, false));
    }

    @Override
    public void onUnequip(Player owner) {
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}

