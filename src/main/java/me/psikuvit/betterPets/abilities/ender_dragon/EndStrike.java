package me.psikuvit.betterPets.abilities.ender_dragon;

import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EndStrike implements IAbility {

    @Override
    public void onEquip(Player paramPlayer) {
    }

    @Override
    public void onUnequip(Player paramPlayer) {
    }

    @Override
    public void handleEvent(Event event, Player owner) {
        if (event instanceof EntityDamageByEntityEvent entityEvent) {
            World world = owner.getWorld();
            Pet pet = playerPetManager.getActivePet(owner);

            if (world.getEnvironment() == World.Environment.THE_END) {

                double damageMultiplier = getAbilityStat().getStatAmplifier(Stats.STRENGTH).getStatAtLevel(pet.getLevel());
                entityEvent.setDamage(entityEvent.getDamage() * (1 + damageMultiplier));
            }
        }
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.STRENGTH, 0.02, 0.02);
    }
}
