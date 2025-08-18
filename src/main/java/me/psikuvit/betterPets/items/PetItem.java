package me.psikuvit.betterPets.items;

import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.Keys;
import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.enums.PetItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class PetItem {
    private final String id;
    private final String displayName;
    private final Material material;
    private final List<String> lore;
    private final PetItemType type;

    public PetItem(String id, String displayName, Material material, List<String> lore, PetItemType type) {
        this.id = id;
        this.displayName = displayName;
        this.material = material;
        this.lore = lore;
        this.type = type;
    }

    public abstract void applyEffect(Pet pet);

    public abstract void removeEffect(Pet pet);

    public ItemStack createItemStack() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Messages.deserialize(displayName));
            meta.lore(lore.stream().map(Messages::deserialize).toList());
            meta.getPersistentDataContainer().set(Keys.PET_ITEM_KEY, PersistentDataType.STRING, id);
        }
        item.setItemMeta(meta);
        return item;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getLore() {
        return lore;
    }

    public PetItemType getType() {
        return type;
    }
}
