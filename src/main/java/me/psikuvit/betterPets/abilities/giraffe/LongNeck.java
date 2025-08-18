package me.psikuvit.betterPets.abilities.giraffe;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LongNeck implements IAbility {
    @Override
    public void onEquip(Player owner) {
        owner.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 20 * 60, 3, true, false));
    }

    @Override
    public void onUnequip(Player owner) {
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.0D, 0.0D);
    }
}
