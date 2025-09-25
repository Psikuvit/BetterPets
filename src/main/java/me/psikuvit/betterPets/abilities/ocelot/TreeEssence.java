package me.psikuvit.betterPets.abilities.ocelot;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class TreeEssence implements IAbility {

    private final Random random = new Random();

    @Override
    public void handleEvent(Event event, Player owner) {
        if (!(event instanceof BlockBreakEvent breakEvent)) return;

        Player player = breakEvent.getPlayer();
        Pet pet = playerPetManager.getActivePet(player);
        if (pet == null) return;

        if (isLog(breakEvent.getBlock().getType())) {
            double expChance = 0.3 * pet.getLevel();

            if (random.nextDouble() * 100 < expChance) {
                breakEvent.setExpToDrop((int) (breakEvent.getExpToDrop() * 1.2));
            }
        }
    }

    private boolean isLog(Material material) {
        return material.name().contains("_LOG") || material.name().contains("_WOOD");
    }

    @Override
    public void onEquip(Player owner) {
        // Event-based ability - no equip action needed
    }

    @Override
    public void onUnequip(Player owner) {
        // Event-based ability - no unequip action needed
    }

    @Override
    public AbilityStats getAbilityStat() {
        return null;
    }
}
