package me.psikuvit.betterPets.abilities.tarantula;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebBattlefield implements IAbility {

    private final Map<UUID, Integer> strengthStacks = new HashMap<>();
    private static final int MAX_STACKS = 10;
    private static final int DURATION_TICKS = 40 * 20; // 40 seconds

    @Override
    public void handleEvent(Event event, Player owner) {
        if (!(event instanceof EntityDeathEvent deathEvent)) return;
        Entity killer = deathEvent.getEntity().getKiller();
        if (!(killer instanceof Player player)) return;
        Pet pet = playerPetManager.getActivePet(player);
        if (pet == null) return;

        double strengthBonus = getAbilityStat().getStatAmplifiers().getFirst().getStatAtLevel(pet.getLevel());

        for (Entity nearby : deathEvent.getEntity().getNearbyEntities(20, 20, 20)) {
            if (nearby instanceof Player p) {
                int currentStrength = strengthStacks.getOrDefault(p.getUniqueId(), 0);
                if (currentStrength < MAX_STACKS) {
                    strengthStacks.put(p.getUniqueId(), currentStrength + 1);

                    StatModifier strengthModifier = new StatModifier(PetUtils.createOtherStatModifier(Stats.STRENGTH.getStat()),
                            Stats.STRENGTH.getStat(), strengthBonus, ModifierOperation.ADD);

                    EcoSkillsAPI.addStatModifier(p, strengthModifier);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            EcoSkillsAPI.removeStatModifier(p, PetUtils.createOtherStatModifier(Stats.STRENGTH.getStat()));
                            int stacks = strengthStacks.getOrDefault(p.getUniqueId(), 1) - 1;
                            if (stacks <= 0) strengthStacks.remove(p.getUniqueId());
                            else strengthStacks.put(p.getUniqueId(), stacks);
                        }
                    }.runTaskLater(JavaPlugin.getProvidingPlugin(getClass()), DURATION_TICKS);
                }
            }
        }
    }


    @Override
    public void onEquip(Player owner) {}
    @Override
    public void onUnequip(Player owner) {}

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats()
                .addStatAmplifier(Stats.STRENGTH, 0.06, 0.06);
    }
}
