package me.psikuvit.betterPets.menu;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.data.PlayerData;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.InventoryUtils;
import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.PetUtils;
import me.psikuvit.betterPets.utils.enums.PetSelectionCause;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PetSelectionGUI implements InventoryHolder {

    private final Inventory inventory;
    private final PetSelectionCause cause;

    public PetSelectionGUI(PetSelectionCause cause) {
        this.cause = cause;
        this.inventory = Bukkit.createInventory(this, 54, Messages.getGuiPetSelectionTitle());
    }

    public void openSelectionMenu(Player player) {
        PlayerData playerData = Main.getInstance().getPetManager().getPlayerData(player);

        List<Pet> availablePets = playerData.getOwnedPets().stream()
                .filter(pet -> pet.getLevel() < 100)
                .filter(pet -> !playerData.isSharedPet(pet))
                .toList();

        inventory.clear();

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, InventoryUtils.BLACK_FILLER);
            inventory.setItem(i + 45, InventoryUtils.BLACK_FILLER);
        }

        for (int i = 0; i < availablePets.size() && i < 36; i++) {
            inventory.setItem(i + 9, PetUtils.petToItem(availablePets.get(i)));
        }

        // Add close button
        inventory.setItem(49, InventoryUtils.CLOSE);

        player.openInventory(inventory);
    }

    public PetSelectionCause getCause() {
        return cause;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
