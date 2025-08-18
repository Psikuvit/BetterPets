package me.psikuvit.betterPets.items.types;

import me.psikuvit.betterPets.items.PetItem;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.enums.PetItemType;
import org.bukkit.Material;

import java.util.List;

public abstract class PerkItem extends PetItem {

    protected PerkItem(String id, String displayName, Material material, List<String> lore) {
        super(id, displayName, material, lore, PetItemType.PERK);
    }

    // Each perk implementation must provide its own effect logic
    @Override
    public abstract void applyEffect(Pet pet);

    @Override
    public abstract void removeEffect(Pet pet);

    // Optional method for perks that need to run periodic tasks
    public void onTick(Pet pet) {
    }
}
