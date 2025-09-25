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

public class WalkingFortress implements IAbility {

    @Override
    public void onEquip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        if (pet == null) return;

        double currentDefense = EcoSkillsAPI.getStatLevel(owner, Stats.DEFENSE.getStat());
        double healthPerDefense = getAbilityStat().getStatAmplifier(Stats.HEALTH).getStatAtLevel(pet.getLevel());
        double totalDefense = (currentDefense / 10) * healthPerDefense;

        StatModifier statModifier = new StatModifier(
                PetUtils.createOtherStatModifier(Stats.HEALTH.getStat()),
                Stats.HEALTH.getStat(),
                totalDefense,
                ModifierOperation.ADD
        );

        EcoSkillsAPI.addStatModifier(owner, statModifier);
    }

    @Override
    public void onUnequip(Player owner) {
        EcoSkillsAPI.removeStatModifier(owner, PetUtils.createOtherStatModifier(Stats.HEALTH.getStat()));
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.HEALTH, 0.01, 0.01);
    }
}
