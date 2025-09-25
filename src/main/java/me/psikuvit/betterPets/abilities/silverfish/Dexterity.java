package me.psikuvit.betterPets.abilities.silverfish;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Dexterity implements IAbility {

    @Override
    public void onEquip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        if (pet == null) return;

        double miningSpeed = getAbilityStat().getStatAmplifiers().getFirst().getStatAtLevel(pet.getLevel());
        int hasteLevel = getHasteLevel(pet.getLevel());
        StatModifier statModifier = new StatModifier(PetUtils.createOtherStatModifier(Stats.MINING_SPEED.getStat()),
                Stats.MINING_SPEED.getStat(), miningSpeed, ModifierOperation.ADD);

        owner.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, hasteLevel - 1, false, false));
        EcoSkillsAPI.addStatModifier(owner, statModifier);
    }

    @Override
    public void onUnequip(Player owner) {
        EcoSkillsAPI.removeStatModifier(owner, PetUtils.createOtherStatModifier(Stats.MINING_SPEED.getStat()));
        owner.removePotionEffect(PotionEffectType.HASTE);
    }

    private int getHasteLevel(int petLevel) {
        if (petLevel >= 100) return 3;
        if (petLevel >= 50) return 2;
        return 1;
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(1.5, 1.5);
    }
}
