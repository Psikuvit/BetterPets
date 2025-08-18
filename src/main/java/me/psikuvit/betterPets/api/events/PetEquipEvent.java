package me.psikuvit.betterPets.api.events;

import me.psikuvit.betterPets.pet.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PetEquipEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Pet pet;
    private boolean cancelled;

    public PetEquipEvent(Player player, Pet pet) {
        this.player = player;
        this.pet = pet;
    }

    public Player getPlayer() {
        return player;
    }

    public Pet getPet() {
        return pet;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
