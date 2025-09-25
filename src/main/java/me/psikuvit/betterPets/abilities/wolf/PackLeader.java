package me.psikuvit.betterPets.abilities.wolf;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import com.willfp.ecoskills.stats.Stat;
import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PackLeader implements IAbility {

    private final Map<UUID, BukkitTask> updateTasks = new HashMap<>();
    private final Map<UUID, Double> currentBonus = new HashMap<>();

    @Override
    public void onEquip(Player owner) {
        currentBonus.put(owner.getUniqueId(), 0.0);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!owner.isOnline()) {
                    cancel();
                    updateTasks.remove(owner.getUniqueId());
                    currentBonus.remove(owner.getUniqueId());
                    return;
                }

                Pet pet = playerPetManager.getActivePet(owner);
                if (pet == null) {
                    cancel();
                    updateTasks.remove(owner.getUniqueId());
                    currentBonus.remove(owner.getUniqueId());
                    return;
                }

                updateCritDamageBonus(owner, pet);
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);

        updateTasks.put(owner.getUniqueId(), task);
    }

    @Override
    public void onUnequip(Player owner) {
        BukkitTask task = updateTasks.remove(owner.getUniqueId());
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }

        removeCurrentBonus(owner);
        currentBonus.remove(owner.getUniqueId());
    }

    private void updateCritDamageBonus(Player owner, Pet pet) {
        long nearbyWolves = owner.getNearbyEntities(16, 16, 16).stream()
                .filter(entity -> entity.getType() == EntityType.WOLF)
                .filter(entity -> !((org.bukkit.entity.Wolf) entity).isTamed())
                .count();

        int wolfCount = Math.min((int) nearbyWolves, 10);

        double bonusPerWolf = 0.15 * pet.getLevel();
        double newBonus = wolfCount * bonusPerWolf;

        Double oldBonus = currentBonus.get(owner.getUniqueId());
        if (oldBonus == null) oldBonus = 0.0;

        if (Math.abs(newBonus - oldBonus) > 0.001) {
            if (oldBonus > 0) {
                removeCurrentBonus(owner);
            }

            if (newBonus > 0) {
                applyNewBonus(owner, newBonus);
            }

            currentBonus.put(owner.getUniqueId(), newBonus);
        }
    }

    private void removeCurrentBonus(Player owner) {
        Stat critDamage = Stats.CRIT_DAMAGE.getStat();
        EcoSkillsAPI.removeStatModifier(owner, PetUtils.createOtherStatModifier(critDamage));
    }

    private void applyNewBonus(Player owner, double bonus) {
        Stat critDamage = Stats.CRIT_DAMAGE.getStat();

        StatModifier statModifier = new StatModifier(
                PetUtils.createOtherStatModifier(critDamage),
                critDamage,
                bonus,
                ModifierOperation.ADD
        );

        EcoSkillsAPI.addStatModifier(owner, statModifier);
    }


    @Override
    public AbilityStats getAbilityStat() {
        return null;
    }
}
