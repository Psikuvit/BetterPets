package me.psikuvit.betterPets.abilities.parrot;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BirdDiscourse implements IAbility {

    private final Map<UUID, List<UUID>> players = new HashMap<>();

    @Override
    public void onEquip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        players.put(owner.getUniqueId(), new ArrayList<>());

        Stat strength = Stats.STRENGTH.getStat();
        UUID uuid = PetUtils.createOtherStatModifier(strength, ModifierOperation.ADD);

        StatModifier statModifier = new StatModifier(uuid, strength,
                getAbilityStat().getValueAtLevel(pet.getLevel()), ModifierOperation.ADD);
        owner.getNearbyEntities(20, 20, 20).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .filter(player -> !players.get(owner.getUniqueId()).contains(player.getUniqueId()))
                .forEach(player -> {
                    EcoSkillsAPI.addStatModifier(player, statModifier);
                    players.get(owner.getUniqueId()).add(player.getUniqueId());
                });

    }

    @Override
    public void onUnequip(Player owner) {
        Stat strength = Stats.STRENGTH.getStat();
        UUID uuid = PetUtils.createOtherStatModifier(strength, ModifierOperation.ADD);

        players.get(owner.getUniqueId()).forEach(player -> EcoSkillsAPI.removeStatModifier(owner, uuid));
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(5.25, 0.25);
    }
}
