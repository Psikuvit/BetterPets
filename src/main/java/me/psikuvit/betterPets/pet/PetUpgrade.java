package me.psikuvit.betterPets.pet;

import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.enums.Rarity;
import org.bukkit.Material;

import java.util.Map;

public class PetUpgrade {

    private final Rarity rarity;
    private final int cost;
    private final long duration;
    private long endTime;
    private final Map<Material, Integer> materials;

    public PetUpgrade(Rarity rarity, int cost, long duration, Map<Material, Integer> materials) {
        this.rarity = rarity;
        this.cost = cost;
        this.duration = duration;
        this.materials = materials;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getCost() {
        return cost;
    }

    public long getDuration() {
        return duration;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getRemainingTime() {
        return Messages.formatTime(getEndTime() - System.currentTimeMillis());
    }

    public String getDurationString() {
        return Messages.formatTime(duration);
    }

    public Map<Material, Integer> getMaterials() {
        return materials;
    }

    public void startUpgrade() {
        this.endTime = System.currentTimeMillis() + duration;
    }

}
