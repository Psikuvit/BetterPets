package me.psikuvit.betterPets.abilities.lion;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KingOfTheJungle implements IAbility {
    @Override
    public void onEquip(Player owner) {
        owner.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 60, 3, true, false));
        owner.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 45, 1, true, false));
    }

    @Override
    public void onUnequip(Player owner) {
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(1.5, 1.5);
    }
}
