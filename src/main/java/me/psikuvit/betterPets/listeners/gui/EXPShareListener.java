package me.psikuvit.betterPets.listeners.gui;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.data.PlayerData;
import me.psikuvit.betterPets.menu.EXPShareGUI;
import me.psikuvit.betterPets.menu.PetSelectionGUI;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.PetSelectionCause;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EXPShareListener implements Listener {

    private final int[] SHARE_SLOTS = {30, 31, 32};

    public EXPShareListener(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        PlayerData playerData = Main.getInstance().getPetManager().getPlayerData(player);

        if (event.getInventory().getHolder() instanceof EXPShareGUI) {
            event.setCancelled(true);
            int slot = event.getRawSlot();

            if (slot == 48) {
                player.closeInventory();
                playerData.getPetsInventory().openPetsMenu(player, 0);
                return;
            }
            if (slot == 49) {
                player.closeInventory();
                return;
            }

            if (isShareSlot(slot)) {
                ItemStack clicked = event.getCurrentItem();

                if (clicked != null) {
                    if (clicked.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                        player.closeInventory();
                        new PetSelectionGUI(PetSelectionCause.EXP_SHARE).openSelectionMenu(player);

                    } else if (PetUtils.isPet(clicked)) {
                        Pet pet = findPetInSharedPets(playerData, PetUtils.extractUUID(clicked));
                        if (pet != null) {

                            player.closeInventory();
                            playerData.removeSharedPet(pet);
                            new EXPShareGUI().openExpMenu(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof EXPShareGUI
                || event.getInventory().getHolder() instanceof PetSelectionGUI) {
            event.setCancelled(true);
        }
    }

    private boolean isShareSlot(int slot) {
        for (int shareSlot : SHARE_SLOTS) {
            if (slot == shareSlot) return true;
        }
        return false;
    }

    private Pet findPetInSharedPets(PlayerData playerData, UUID uuid) {
        return playerData.getSharedPets().stream()
                .filter(pet -> pet.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }
}
