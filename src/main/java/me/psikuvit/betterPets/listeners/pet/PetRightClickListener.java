package me.psikuvit.betterPets.listeners.pet;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.PlayerPetManager;
import me.psikuvit.betterPets.api.events.PetInteractEvent;
import me.psikuvit.betterPets.items.PetItem;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.pet.PetSkin;
import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.PetUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PetRightClickListener implements Listener {

    private final Main plugin;
    private final PlayerPetManager manager;

    public PetRightClickListener(Main plugin) {
        this.plugin = plugin;
        this.manager = plugin.getPetManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPetRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clicked = event.getRightClicked();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Handle interaction with either the ItemDisplay or Interaction entity
        if (clicked instanceof ItemDisplay || clicked instanceof Interaction) {
            UUID petUUID = PetUtils.extractUUID(clicked);
            if (petUUID == null) return;

            Pet pet = manager.getPetByUUID(player, petUUID);
            if (pet == null) return;

            PetInteractEvent petInteractEvent = new PetInteractEvent(player, pet);
            Bukkit.getPluginManager().callEvent(petInteractEvent);
            if (petInteractEvent.isCancelled()) return;

            if (PetUtils.isSkinItem(item)) {
                PetSkin skin = plugin.getPetSkinLoader().getSkinById(PetUtils.getSkinId(item));
                if (skin == null) {
                    player.sendMessage(Messages.getSkinNotFound());
                    return;
                }
                String petId = pet.getId().substring(0, pet.getId().lastIndexOf('_'));
                String skinPetId = skin.petId();

                if (!petId.equals(skinPetId)) {
                    player.sendMessage(Messages.getSkinIncompatible());
                    return;
                }

                pet.setPetSkin(skin);
                item.setAmount(item.getAmount() - 1);
                player.sendMessage(Messages.getSkinEquipped(skin, pet));
            } else if (PetUtils.isPetItem(item)) {
                PetItem petItem = plugin.getPetItemLoader().getItem(PetUtils.getPetItemId(item));
                if (petItem == null) {
                    player.sendMessage("error");
                    return;
                }
                pet.equipItem(petItem);
                item.setAmount(item.getAmount() - 1);
            }
        }
    }
}
