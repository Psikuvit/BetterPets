package me.psikuvit.betterPets.abilities.blue_whale;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class Ingest implements IAbility {

    @Override
    public void onEquip(Player owner) {
        int potions = owner.getActivePotionEffects().size();
        Pet pet = playerPetManager.getActivePet(owner);

        double healthBonus = potions * getAbilityStat().getStatAmplifier(Stats.HEALTH).getStatAtLevel(pet.getLevel());

        StatModifier statModifier = new StatModifier(PetUtils.createOtherStatModifier(Stats.HEALTH.getStat()), Stats.HEALTH.getStat(),
                healthBonus, ModifierOperation.ADD);

        EcoSkillsAPI.addStatModifier(owner, statModifier);
    }

    @Override
    public void onUnequip(Player owner) {
        EcoSkillsAPI.removeStatModifier(owner, PetUtils.createOtherStatModifier(Stats.HEALTH.getStat()));
    }


    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.HEALTH, 2.5, 2.5);
    }
}
