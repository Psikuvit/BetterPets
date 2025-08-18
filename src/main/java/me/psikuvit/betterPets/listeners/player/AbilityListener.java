package me.psikuvit.betterPets.listeners.player;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.PlayerPetManager;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.data.PlayerData;
import me.psikuvit.betterPets.pet.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class AbilityListener implements Listener {

    private final PlayerPetManager petManager;

    public AbilityListener(Main plugin) {
        this.petManager = plugin.getPetManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        applyAbility(event.getPlayer(), event);

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Player player = null;

        if (event.getDamager() instanceof Player) {
            player = (Player) event.getDamager();
        } else if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        }
        if (player == null) return;

        applyAbility(player, event);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        applyAbility(event.getPlayer(), event);
    }

    public void applyAbility(Player player, Event event) {
        PlayerData data = petManager.getPlayerData(player);
        if (data == null || !data.hasActivePet()) {
            return;
        }

        Pet pet = data.getActivePet();
        for (PetAbility ability : pet.getAbilities()) ability.handleEvent(event, player);
    }
}
