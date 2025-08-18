package me.psikuvit.betterPets.listeners.player;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.PlayerPetManager;
import me.psikuvit.betterPets.data.PlayerData;
import me.psikuvit.betterPets.database.DatabaseManager;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private final Main plugin;
    private final PlayerPetManager manager;
    private final DatabaseManager databaseManager;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
        this.manager = plugin.getPetManager();
        this.databaseManager = plugin.getDatabaseManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = new PlayerData(player);
        manager.setPlayerData(player, playerData);

        databaseManager.loadPlayerData(player.getUniqueId(), playerData).thenAccept(loadedData -> {
            Messages.debug("Loaded player data for: " + player.getName());
            if (loadedData != null) {
                Pet offlinePet = loadedData.getOfflinePet();
                if (offlinePet != null) {
                    Bukkit.getScheduler().runTask(plugin, () -> manager.activatePet(player, offlinePet));
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = manager.getPlayerData(player);

        Pet activePet = playerData.getActivePet();
        if (activePet != null) {
            playerData.setOfflinePet(activePet);
            Messages.debug("Saving active pet: " + activePet.getId() + " for player: " + player.getName());
            manager.deactivatePet(player);
        }
        manager.removePlayerData(player);
        databaseManager.savePlayerData(player.getUniqueId(), playerData);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = manager.getPlayerData(player);

        if (playerData != null && playerData.getActivePet() != null) {
            Pet activePet = playerData.getActivePet();
            manager.deactivatePet(player);

            Bukkit.getScheduler().runTaskLater(plugin, () -> manager.activatePet(player, activePet), 5L);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = manager.getPlayerData(player);

        if (playerData != null && playerData.getActivePet() != null) {
            Pet activePet = playerData.getActivePet();
            manager.deactivatePet(player);

            Bukkit.getScheduler().runTaskLater(plugin, () -> manager.activatePet(player, activePet), 5L);

        }
    }
}
