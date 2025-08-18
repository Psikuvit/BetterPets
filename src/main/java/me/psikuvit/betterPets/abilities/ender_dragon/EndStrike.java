package me.psikuvit.betterPets.abilities.ender_dragon;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
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
                entityEvent.setDamage(entityEvent.getDamage() * getAbilityStat().getValueAtLevel(pet.getLevel()));
            }
        }
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(2, 2);
    }
}
