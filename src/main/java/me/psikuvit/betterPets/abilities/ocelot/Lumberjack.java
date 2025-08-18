package me.psikuvit.betterPets.abilities.ocelot;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Lumberjack implements IAbility {
    @Override
    public void onEquip(Player owner) {
        owner.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 20 * 60, 2, true, false));
    }

    @Override
    public void onUnequip(Player owner) {
        // no-op
    }

    @Override
    public AbilityStat getAbilityStat() {
        // Active ability, stat not used in logic; keep neutral scaling
        return new AbilityStat(0.0D, 0.0D);
    }
}
