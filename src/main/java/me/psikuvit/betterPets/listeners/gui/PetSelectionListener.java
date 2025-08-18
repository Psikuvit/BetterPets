package me.psikuvit.betterPets.listeners.gui;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.autopet.AutoPetRuleCreationSession;
import me.psikuvit.betterPets.data.PlayerData;
import me.psikuvit.betterPets.menu.EXPShareGUI;
import me.psikuvit.betterPets.menu.PetSelectionGUI;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.PetSelectionCause;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PetSelectionListener implements Listener {

    private final Main plugin;

    public PetSelectionListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof org.bukkit.entity.Player player)) return;

        PlayerData playerData = plugin.getPetManager().getPlayerData(player);
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType().isAir()) return;

        if (event.getInventory().getHolder() instanceof PetSelectionGUI petSelectionGUI) {
            event.setCancelled(true);
            int slot = event.getRawSlot();

            if (petSelectionGUI.getCause().equals(PetSelectionCause.EXP_SHARE)) {
                if (slot == 49) {
                    player.closeInventory();
                    playerData.getPetsInventory().getExpShareGUI().openExpMenu(player);
                } else if (PetUtils.isPet(clicked)) {
                    Pet pet = playerData.getPetByUUID(PetUtils.extractUUID(clicked));
                    if (pet != null && pet.getLevel() < 100 && !playerData.isSharedPet(pet)) {
                        playerData.addSharedPet(pet);
                        player.closeInventory();
                        new EXPShareGUI().openExpMenu(player);
                    }
                }
            } else if (petSelectionGUI.getCause().equals(PetSelectionCause.PET_RULE)) {
                if (PetUtils.isPet(clicked)) {
                    Pet pet = playerData.getPetByUUID(PetUtils.extractUUID(clicked));

                    AutoPetRuleCreationSession session = plugin.getAutoPetRuleService().getSession(player);
                    session.setSelectedPet(pet);

                    plugin.getAutoPetRuleService().createRule(player);
                    player.closeInventory();
                }
            }
        }
    }
}
