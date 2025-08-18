package me.psikuvit.betterPets.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public record PetAbility(String name, List<String> description, IAbility ability) {

    public void onEquip(Player owner) {
        this.ability.onEquip(owner);
    }

    public void onUnequip(Player owner) {
        this.ability.onUnequip(owner);
    }

    public void handleEvent(Event event, Player owner) {
        this.ability.handleEvent(event, owner);
    }

    public double getStatValueAtLevel(int level) {
        return this.ability.getAbilityStat().getValueAtLevel(level);
    }
}