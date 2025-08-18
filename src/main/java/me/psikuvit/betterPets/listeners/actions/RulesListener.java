package me.psikuvit.betterPets.listeners.actions;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.PlayerPetManager;
import me.psikuvit.betterPets.autopet.AutoPetRule;
import me.psikuvit.betterPets.autopet.TriggerType;
import me.psikuvit.betterPets.data.PlayerData;
import me.psikuvit.betterPets.pet.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerAnimationEvent;

public class RulesListener implements Listener {

    private final PlayerPetManager manager;

    public RulesListener(Main plugin) {
        this.manager = plugin.getPetManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = manager.getPlayerData(player);
        if (playerData.hasAutoPetRule(TriggerType.BREAK_BLOCK)) {
            AutoPetRule rule = playerData.getAutoPetRule(TriggerType.BREAK_BLOCK);
            Pet newPet = rule.getSelectedPet();
            manager.deactivatePet(player);
            manager.activatePet(player, newPet);
        }
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = manager.getPlayerData(player);
        if (playerData.hasAutoPetRule(TriggerType.PLACE_BLOCK)) {
            AutoPetRule rule = playerData.getAutoPetRule(TriggerType.PLACE_BLOCK);
            Pet newPet = rule.getSelectedPet();
            manager.deactivatePet(player);
            manager.activatePet(player, newPet);
        }
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
    public void onSwing(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = manager.getPlayerData(player);
        if (playerData.hasAutoPetRule(TriggerType.SWING_SWORD)) {
            AutoPetRule rule = playerData.getAutoPetRule(TriggerType.SWING_SWORD);
            Pet newPet = rule.getSelectedPet();
            manager.deactivatePet(player);
            manager.activatePet(player, newPet);
        }
    }
}
