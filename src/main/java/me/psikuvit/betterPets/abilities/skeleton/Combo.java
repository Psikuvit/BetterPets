package me.psikuvit.betterPets.abilities.skeleton;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Combo implements IAbility {

    private final Map<UUID, Integer> comboStacks = new HashMap<>();
    private final Map<UUID, BukkitTask> stackExpirationTasks = new HashMap<>();

    private static final double STRENGTH_PER_STACK = 3.0;
    private static final int STACK_DURATION_TICKS = 160;

    @Override
    public void onEquip(Player owner) {
        comboStacks.put(owner.getUniqueId(), 0);
    }

    @Override
    public void onUnequip(Player owner) {
        BukkitTask task = stackExpirationTasks.remove(owner.getUniqueId());
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }

        removeStrengthModifier(owner);
        comboStacks.remove(owner.getUniqueId());
    }

    @Override
    public void handleEvent(Event event, Player owner) {
        if (!(event instanceof EntityDamageByEntityEvent damageEvent)) return;
        if (!(damageEvent.getDamager() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player shooter)) return;
        if (!shooter.equals(owner)) return;
        if (!(damageEvent.getEntity() instanceof LivingEntity)) return;

        Pet pet = playerPetManager.getActivePet(owner);
        if (pet == null) return;

        // Add combo stack
        addComboStack(owner, pet);
    }

    private void addComboStack(Player owner, Pet pet) {
        int currentStacks = comboStacks.getOrDefault(owner.getUniqueId(), 0);

        double maxStacksDouble = getAbilityStat().getStatAmplifiers().getFirst().getStatAtLevel(pet.getLevel());
        int maxStacks = (int) Math.floor(maxStacksDouble);

        if (currentStacks >= maxStacks) return;

        int newStacks = currentStacks + 1;
        comboStacks.put(owner.getUniqueId(), newStacks);

        updateStrengthModifier(owner, newStacks);

        BukkitTask existingTask = stackExpirationTasks.get(owner.getUniqueId());
        if (existingTask != null && !existingTask.isCancelled()) {
            existingTask.cancel();
        }

        BukkitTask expirationTask = new BukkitRunnable() {
            @Override
            public void run() {
                clearAllStacks(owner);
            }
        }.runTaskLater(Main.getInstance(), STACK_DURATION_TICKS);

        stackExpirationTasks.put(owner.getUniqueId(), expirationTask);
    }

    private void clearAllStacks(Player owner) {
        comboStacks.put(owner.getUniqueId(), 0);
        removeStrengthModifier(owner);
        stackExpirationTasks.remove(owner.getUniqueId());
    }

    private void updateStrengthModifier(Player owner, int stacks) {
        removeStrengthModifier(owner);

        if (stacks > 0) {
            double strengthBonus = STRENGTH_PER_STACK * stacks;

            StatModifier strengthModifier = new StatModifier(
                    PetUtils.createOtherStatModifier(Stats.STRENGTH.getStat()),
                    Stats.STRENGTH.getStat(),
                    strengthBonus,
                    ModifierOperation.ADD
            );

            EcoSkillsAPI.addStatModifier(owner, strengthModifier);
        }
    }

    private void removeStrengthModifier(Player owner) {
        EcoSkillsAPI.removeStatModifier(owner, PetUtils.createOtherStatModifier(Stats.STRENGTH.getStat()));
    }


    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(0.2, 0.2);
    }
}
