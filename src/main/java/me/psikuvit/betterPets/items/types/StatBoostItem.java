package me.psikuvit.betterPets.items.types;

import com.willfp.ecoskills.stats.Stat;
import me.psikuvit.betterPets.items.PetItem;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.pet.PetAttribute;
import me.psikuvit.betterPets.utils.enums.PetItemType;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatBoostItem extends PetItem {

    private final Map<Stat, Double> stats;

    public StatBoostItem(String id, String displayName, Material material, List<String> lore) {
        super(id, displayName, material, lore, PetItemType.STAT);
        this.stats = new HashMap<>();
    }

    public void addStatBoost(Stat stat, double amount) {
        stats.put(stat, amount);
    }

    @Override
    public void applyEffect(Pet pet) {
        for (PetAttribute attribute : pet.getAttributes()) {
            if (stats.containsKey(attribute.getStat())) {
                attribute.addBoost(stats.get(attribute.getStat()));
                pet.applyAttributes();
            }
        }
    }

    @Override
    public void removeEffect(Pet pet) {
        for (PetAttribute attribute : pet.getAttributes()) {
            if (stats.containsKey(attribute.getStat())) {
                attribute.removeBoost(stats.get(attribute.getStat()));
                pet.removeAttributes();
            }
        }
    }

    public Map<Stat, Double> getStats() {
        return new HashMap<>(stats);
    }
}
