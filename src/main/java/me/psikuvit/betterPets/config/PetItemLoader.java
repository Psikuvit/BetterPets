package me.psikuvit.betterPets.config;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.items.PetItem;
import me.psikuvit.betterPets.items.types.ExpBoostItem;
import me.psikuvit.betterPets.items.types.PerkItem;
import me.psikuvit.betterPets.items.types.StatBoostItem;
import me.psikuvit.betterPets.utils.enums.PetItemType;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class PetItemLoader {

    private final Map<String, PetItem> items;
    private final Main plugin;
    private final File itemFolder;

    public PetItemLoader(Main plugin) {
        this.plugin = plugin;
        this.items = new HashMap<>();
        this.itemFolder = new File(plugin.getDataFolder(), "items");
        createItemFolder();
    }

    private void createItemFolder() {
        if (!itemFolder.exists()) {
            itemFolder.mkdirs();
            plugin.saveResource("items/example_stat_boost.yml", false);
            plugin.saveResource("items/example_exp_boost.yml", false);
            plugin.saveResource("items/example_perk.yml", false);
        }
    }

    public void loadItems() {
        items.clear();
        File[] files = itemFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            try {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                String id = config.getString("id", file.getName().replace(".yml", ""));
                loadItem(id, config);
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "Failed to load pet item from " + file.getName(), e);
            }
        }
        plugin.getLogger().info("Loaded " + items.size() + " pet items");
    }

    private void loadItem(String id, YamlConfiguration config) {
        try {
            String type = config.getString("type", "");
            ConfigurationSection itemSection = config.getConfigurationSection("item");
            if (itemSection == null) {
                plugin.getLogger().warning("Invalid item configuration for " + id + ": missing item section");
                return;
            }

            String name = itemSection.getString("display-name", id);
            Material material = Material.valueOf(itemSection.getString("material", "BARRIER").toUpperCase());
            List<String> lore = itemSection.getStringList("lore");

            PetItem item = switch (PetItemType.valueOf(type.toUpperCase())) {
                case STAT -> {
                    StatBoostItem statItem = new StatBoostItem(id, name, material, lore);
                    ConfigurationSection stats = config.getConfigurationSection("stats");
                    if (stats != null) {
                        for (String key : stats.getKeys(false))
                            statItem.addStatBoost(Stats.valueOf(key).getStat(), stats.getDouble(key));
                    }
                    yield statItem;
                }
                case EXP_BOOST -> {
                    double amount = config.getDouble("amount", 0);
                    yield new ExpBoostItem(id, name, material, lore, amount);
                }
                case PERK -> {
                    String className = config.getString("class");
                    if (className == null) {
                        plugin.getLogger().warning("Invalid perk configuration for " + id + ": missing class");
                        yield null;
                    }
                    try {
                        Class<?> perkClass = Class.forName(className);
                        if (!PerkItem.class.isAssignableFrom(perkClass)) {
                            plugin.getLogger().warning("Invalid perk class: " + className + " must extend PerkItem");
                            yield null;
                        }
                        Constructor<?> constructor = perkClass.getConstructor(String.class, String.class, Material.class, List.class);
                        yield (PetItem) constructor.newInstance(id, name, material, lore);
                    } catch (Exception e) {
                        plugin.getLogger().log(Level.WARNING, "Failed to load perk class: " + className, e);
                        yield null;
                    }
                }
            };

            if (item != null) {
                items.put(id, item);
                plugin.getLogger().info("Loaded pet item: " + id);
            }
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid item configuration for " + id + ": " + e.getMessage());
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Error loading item " + id, e);
        }
    }

    public PetItem getItem(String id) {
        return items.get(id);
    }

    public Map<String, PetItem> getItems() {
        return new HashMap<>(items);
    }
}
