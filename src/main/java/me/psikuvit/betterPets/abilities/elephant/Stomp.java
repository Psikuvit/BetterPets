package me.psikuvit.betterPets.abilities.elephant;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class Stomp implements IAbility {

    @Override
    public void onEquip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        if (pet == null) return;

        double currentSpeed = EcoSkillsAPI.getStatLevel(owner, Stats.SPEED.getStat());
        double defensePerHundredSpeed = getAbilityStat().getStatAmplifier(Stats.DEFENSE).getStatAtLevel(pet.getLevel());
        double totalDefense = (currentSpeed / 100.0) * defensePerHundredSpeed;

        StatModifier statModifier = new StatModifier(
            PetUtils.createOtherStatModifier(Stats.DEFENSE.getStat()),
            Stats.DEFENSE.getStat(),
            totalDefense,
            ModifierOperation.ADD
        );

        EcoSkillsAPI.addStatModifier(owner, statModifier);
    }

    @Override
    public void onUnequip(Player owner) {
        EcoSkillsAPI.removeStatModifier(owner, PetUtils.createOtherStatModifier(Stats.DEFENSE.getStat()));
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.DEFENSE, 0.2, 0.2);
    }
}
