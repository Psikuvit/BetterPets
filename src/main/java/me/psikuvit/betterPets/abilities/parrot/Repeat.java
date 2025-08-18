package me.psikuvit.betterPets.abilities.parrot;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Repeat implements IAbility {

    public void onEquip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        for (PotionEffect effect : owner.getActivePotionEffects()) {
            int duration = effect.getDuration();
            double amplifier = getAbilityStat().getValueAtLevel(pet.getLevel());
            int newDuration = (int) (duration * (1.0D + amplifier / 100.0D));
            owner.removePotionEffect(effect.getType());
            owner.addPotionEffect(new PotionEffect(effect.getType(), newDuration, effect.getAmplifier(), effect.isAmbient(), effect.hasParticles(), effect.hasIcon()));
        }
    }

    public void onUnequip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        for (PotionEffect effect : owner.getActivePotionEffects()) {
            int duration = effect.getDuration();
            double amplifier = getAbilityStat().getValueAtLevel(pet.getLevel());
            int originalDuration = (int) (duration / (1.0D + amplifier / 100.0D));
            owner.removePotionEffect(effect.getType());
            owner.addPotionEffect(new PotionEffect(effect.getType(), originalDuration, effect.getAmplifier(), effect.isAmbient(), effect.hasParticles(), effect.hasIcon()));
        }
    }

    public boolean isPassive() {
        return true;
    }

    public AbilityStat getAbilityStat() {
        return new AbilityStat(5.3D, 0.35D);
    }
}
