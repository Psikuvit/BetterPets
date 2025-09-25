package me.psikuvit.betterPets.abilities.giraffe;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class HigherGround implements IAbility {

    @Override
    public void onEquip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        if (getAbilityStat() == null) return;

        double currentSwingRange = EcoSkillsAPI.getStatLevel(owner, Stats.SWING_RANGE.getStat());

        double effectiveSwingRange = Math.min(currentSwingRange, 6.0);
        double swingRangeOver3 = Math.max(0, effectiveSwingRange - 3.0);

        double bonusMultiplier = swingRangeOver3 / 0.1;

        double percentagePerUnit = getAbilityStat().getStatAmplifier(Stats.STRENGTH).getStatAtLevel(pet.getLevel());
        double totalPercentageBonus = (bonusMultiplier * percentagePerUnit) / 100.0;

        double currentCritDamage = EcoSkillsAPI.getStatLevel(owner, Stats.CRIT_DAMAGE.getStat());
        double currentStrength = EcoSkillsAPI.getStatLevel(owner, Stats.STRENGTH.getStat());

        double critDamageBonus = currentCritDamage * totalPercentageBonus;
        double strengthBonus = currentStrength * totalPercentageBonus;

        StatModifier critDamageModifier = new StatModifier(
            PetUtils.createOtherStatModifier(Stats.CRIT_DAMAGE.getStat()),
            Stats.CRIT_DAMAGE.getStat(),
            critDamageBonus,
            ModifierOperation.ADD
        );
        EcoSkillsAPI.addStatModifier(owner, critDamageModifier);

        StatModifier strengthModifier = new StatModifier(
            PetUtils.createOtherStatModifier(Stats.STRENGTH.getStat()),
            Stats.STRENGTH.getStat(),
            strengthBonus,
            ModifierOperation.ADD
        );
        EcoSkillsAPI.addStatModifier(owner, strengthModifier);
    }

    @Override
    public void onUnequip(Player owner) {
        removeAbilityStats(owner);
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.CRIT_DAMAGE, 0.0015, 0.0015)
                .addStatAmplifier(Stats.STRENGTH, 0.0015, 0.0015);
    }
}
