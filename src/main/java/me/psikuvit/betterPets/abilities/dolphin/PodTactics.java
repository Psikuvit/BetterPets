package me.psikuvit.betterPets.abilities.dolphin;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PodTactics implements IAbility {

    private static final Map<Player, BukkitTask> activeTasks = new HashMap<>();
    private static final Map<Player, Set<Player>> buffedPlayers = new HashMap<>();
    private static final double RANGE = 30;
    private static final int MAX_PLAYERS = 5;

    @Override
    public void onEquip(Player owner) {
        Pet pet = playerPetManager.getActivePet(owner);
        if (pet == null) return;

        buffedPlayers.put(owner, new HashSet<>());

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(),
                () -> updateNearbyPlayerBuffs(owner, pet), 0L, 20L);

        activeTasks.put(owner, task);
    }

    @Override
    public void onUnequip(Player owner) {
        BukkitTask task = activeTasks.remove(owner);
        if (task != null) {
            task.cancel();
        }

        Set<Player> previouslyBuffed = buffedPlayers.remove(owner);
        if (previouslyBuffed != null) {
            for (Player buffedPlayer : previouslyBuffed) {
                EcoSkillsAPI.removeStatModifier(buffedPlayer,
                        PetUtils.createOtherStatModifier(Stats.FISHING_SPEED.getStat()));
            }
        }
    }

    private void updateNearbyPlayerBuffs(Player owner, Pet pet) {
        if (owner == null || !owner.isOnline()) return;

        Set<Player> currentlyBuffed = buffedPlayers.get(owner);
        if (currentlyBuffed == null) return;

        Set<Player> nearbyPlayers = new HashSet<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(owner.getWorld()) &&
                player.getLocation().distance(owner.getLocation()) <= RANGE) {
                nearbyPlayers.add(player);
                if (nearbyPlayers.size() >= MAX_PLAYERS) break;
            }
        }

        double fishingSpeedPerPlayer = 0.1 + (0.1 * pet.getLevel());
        double totalFishingSpeed = fishingSpeedPerPlayer * nearbyPlayers.size();

        Set<Player> playersToUnbuff = new HashSet<>(currentlyBuffed);
        playersToUnbuff.removeAll(nearbyPlayers);
        for (Player player : playersToUnbuff) {
            EcoSkillsAPI.removeStatModifier(player,
                    PetUtils.createOtherStatModifier(Stats.FISHING_SPEED.getStat()));
        }

        for (Player nearbyPlayer : nearbyPlayers) {
            if (currentlyBuffed.contains(nearbyPlayer)) {
                EcoSkillsAPI.removeStatModifier(nearbyPlayer,
                        PetUtils.createOtherStatModifier(Stats.FISHING_SPEED.getStat()));
            }

            StatModifier statModifier = new StatModifier(
                PetUtils.createOtherStatModifier(Stats.FISHING_SPEED.getStat()),
                Stats.FISHING_SPEED.getStat(),
                totalFishingSpeed,
                ModifierOperation.ADD
            );
            EcoSkillsAPI.addStatModifier(nearbyPlayer, statModifier);
        }

        currentlyBuffed.clear();
        currentlyBuffed.addAll(nearbyPlayers);
    }

    @Override
    public AbilityStats getAbilityStat() {
        return null;
    }
}
