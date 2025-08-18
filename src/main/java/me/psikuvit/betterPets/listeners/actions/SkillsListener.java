package me.psikuvit.betterPets.listeners.actions;

import com.willfp.ecoskills.api.event.PlayerSkillXPGainEvent;
import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.enums.XPSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class SkillsListener implements Listener {

    private final Main plugin;

    public SkillsListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onXpGain(PlayerSkillXPGainEvent event) {
        Player player = event.getPlayer();
        Pet activePet = plugin.getPetManager().getActivePet(player);
        Set<Pet> sharedPets = plugin.getPetManager().getPlayerSharedPets(player);

        if (activePet == null) return;

        try {
            XPSource xpSource = XPSource.valueOf(event.getSkill().getId().toUpperCase());

            activePet.gainExp((int) event.getGainedXP(), xpSource);
            sharedPets.forEach(pet -> pet.gainExp((int) event.getGainedXP(), xpSource));

        } catch (IllegalArgumentException e) {
            Messages.debug("Unknown skill ID: " + event.getSkill().getId() + ". Cannot process XP gain.");
        }
    }
}
