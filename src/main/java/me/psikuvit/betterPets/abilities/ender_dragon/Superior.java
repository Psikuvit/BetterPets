package me.psikuvit.betterPets.abilities.ender_dragon;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import com.willfp.ecoskills.stats.Stat;
import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;

public class Superior implements IAbility {

    private final String[] stats = {
            "health", "strength", "defense", "speed",
            "crit_chance", "crit_damage", "wisdom",
            "ferocity", "attack_speed", "ability_damage"
    };

    @Override
    public void onEquip(Player paramPlayer) {
        Pet pet = playerPetManager.getActivePet(paramPlayer);

        for (String statString : stats) {
            Stat stat = Stats.valueOf(statString.toUpperCase()).getStat();

            double statValue = EcoSkillsAPI.getStatLevel(paramPlayer, stat);
            statValue = statValue * (1 + getAbilityStat().getValueAtLevel(pet.getLevel()));

            StatModifier modifier = new StatModifier(PetUtils.createOtherStatModifier(stat, ModifierOperation.ADD), stat, statValue, ModifierOperation.ADD);
            EcoSkillsAPI.addStatModifier(paramPlayer, modifier);

        }
    }

    @Override
    public void onUnequip(Player paramPlayer) {
        for (String statString : stats) {
            Stat stat = Stats.valueOf(statString.toUpperCase()).getStat();
            EcoSkillsAPI.removeStatModifier(paramPlayer, PetUtils.createOtherStatModifier(stat, ModifierOperation.ADD));

        }
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.1, 0.1);
    }
}
