package me.psikuvit.betterPets.abilities.wither_skeleton;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherImmunity implements IAbility {
    @Override
    public void onEquip(Player owner) {
        // Provide constant fire resistance while equipped
        owner.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 300, 0, true, false));
    }

    @Override
    public void onUnequip(Player owner) {
    }

    

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}
