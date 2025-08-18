package me.psikuvit.betterPets.abilities;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.PlayerPetManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public interface IAbility {

    PlayerPetManager playerPetManager = Main.getInstance().getPetManager();

    void onEquip(Player paramPlayer);

    void onUnequip(Player paramPlayer);

    default void handleEvent(Event event, Player owner) {
    }

    AbilityStat getAbilityStat();
}
