package me.psikuvit.betterPets.abilities.blue_whale;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WhaleCall implements IAbility {
    @Override
    public void onEquip(Player owner) {
        owner.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 300, 0, true, false));
        owner.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20 * 300, 2, true, false));
    }

    @Override
    public void onUnequip(Player owner) {
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}
