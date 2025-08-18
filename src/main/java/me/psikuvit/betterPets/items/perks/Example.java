package me.psikuvit.betterPets.items.perks;

import me.psikuvit.betterPets.items.types.PerkItem;
import me.psikuvit.betterPets.pet.Pet;
import org.bukkit.Material;

import java.util.List;

public class Example extends PerkItem {

    public Example(String id, String displayName, Material material, List<String> lore) {
        super(id, displayName, material, lore);
    }

    @Override
    public void applyEffect(Pet pet) {

    }

    @Override
    public void removeEffect(Pet pet) {

    }
}
