package me.psikuvit.betterPets.listeners.gui;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.autopet.AutoPetRuleService;
import me.psikuvit.betterPets.autopet.TriggerType;
import me.psikuvit.betterPets.menu.AutoPetRuleMenu;
import me.psikuvit.betterPets.utils.Keys;
import me.psikuvit.betterPets.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class AutoPetListener implements Listener {

    private final AutoPetRuleService service;

    public AutoPetListener(Main plugin) {
        this.service = plugin.getAutoPetRuleService();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getInventory().getHolder() instanceof AutoPetRuleMenu autoPetRuleMenu) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            if (item == null) return;

            switch (autoPetRuleMenu.getMenuType()) {
                case RULE_MANAGEMENT -> {
                    if (event.getSlot() == 49) {
                        player.closeInventory();
                    } else if (event.getSlot() == 50) {
                        autoPetRuleMenu.openMenu(AutoPetRuleMenu.MenuType.TRIGGER_SELECTION);
                    }
                }
                case TRIGGER_SELECTION -> {
                    if (event.getSlot() == 49) {
                        player.closeInventory();
                    } else if (item.getItemMeta().getPersistentDataContainer().has(Keys.TRIGGER_KEY)) {
                        try {
                            String trigger = item.getItemMeta().getPersistentDataContainer().get(Keys.TRIGGER_KEY, PersistentDataType.STRING);
                            TriggerType type = TriggerType.valueOf(trigger);
                            service.getSession(player).setTriggerType(type);

                            autoPetRuleMenu.openMenu(AutoPetRuleMenu.MenuType.PET_SELECTION);
                        } catch (Exception e) {
                            Messages.debug("Could not parse trigger type: " + item.getItemMeta().getPersistentDataContainer().get(Keys.TRIGGER_KEY, PersistentDataType.STRING));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (event.getInventory().getHolder() instanceof AutoPetRuleMenu) {
            if (event.getReason() == InventoryCloseEvent.Reason.OPEN_NEW) return;
            service.completeSession(player);
        }

    }
}
