package me.psikuvit.betterPets.abilities;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.api.modifiers.ModifierOperation;
import com.willfp.ecoskills.api.modifiers.StatModifier;
import com.willfp.ecoskills.stats.Stat;
import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.PlayerPetManager;
import me.psikuvit.betterPets.hooks.WorldGuardHook;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public interface IAbility {

    PlayerPetManager playerPetManager = Main.getInstance().getPetManager();
    MountManager mountManager = Main.getInstance().getMountManager();
    WorldGuardHook worldGuard = Main.getInstance().getWorldGuardHook();

    void onEquip(Player paramPlayer);
    void onUnequip(Player paramPlayer);
    default void handleEvent(Event event, Player owner) {}
    AbilityStats getAbilityStat();

    default void applyAbilityStats(Player player) {
        Pet pet = playerPetManager.getActivePet(player);
        if (getAbilityStat() == null) return;
        for (AbilityStats.StatAmplifier statAmplifier : getAbilityStat().getStatAmplifiers()) {

            Stat stat = statAmplifier.stat().getStat();
            StatModifier statModifier = new StatModifier(
                    PetUtils.createOtherStatModifier(stat),
                    stat,
                    statAmplifier.getStatAtLevel(pet.getLevel()),
                    ModifierOperation.ADD
            );

            EcoSkillsAPI.addStatModifier(player, statModifier);
        }
    }

    default void removeAbilityStats(Player player) {
        if (getAbilityStat() == null) return;
        for (AbilityStats.StatAmplifier statAmplifier : getAbilityStat().getStatAmplifiers()) {
            Stat stat = statAmplifier.stat().getStat();
            EcoSkillsAPI.removeStatModifier(player, PetUtils.createOtherStatModifier(stat));
        }
    }
}
