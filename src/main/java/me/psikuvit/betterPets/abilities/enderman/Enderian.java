package me.psikuvit.betterPets.abilities.enderman;

import me.psikuvit.betterPets.abilities.AbilityStat;
import me.psikuvit.betterPets.abilities.IAbility;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Enderian implements IAbility {

    @Override
    public void onEquip(Player paramPlayer) {
    }

    @Override
    public void onUnequip(Player paramPlayer) {
    }

    @Override
    public void handleEvent(Event event, Player owner) {
        if (event instanceof EntityDamageByEntityEvent entityEvent) {
            if (entityEvent.getDamager() instanceof LivingEntity damager) {
                if (damager.getWorld().getEnvironment() == World.Environment.THE_END) {
                    double damageMultiplier = getAbilityStat().getValueAtLevel(owner.getLevel());
                    entityEvent.setDamage(entityEvent.getDamage() * (1 - damageMultiplier));

                }
            }
        }
    }

    @Override
    public AbilityStat getAbilityStat() {
        return new AbilityStat(0.3, 0.3);
    }
}
