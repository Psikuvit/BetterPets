# BetterPets API Documentation v2.0

This document provides comprehensive documentation for the BetterPets API v2.0, allowing other plugin developers to interact with the complete pet system.

## Table of Contents

1. [Getting Started](#getting-started)
2. [API Classes Overview](#api-classes-overview)
3. [Core API Methods](#core-api-methods)
4. [Pet Management](#pet-management)
5. [Experience and Leveling](#experience-and-leveling)
6. [Pet Items and Customization](#pet-items-and-customization)
7. [XP Sharing System](#xp-sharing-system)
8. [AutoPet Rules](#autopet-rules)
9. [Pet Upgrade System](#pet-upgrade-system)
10. [Pet Abilities](#pet-abilities)
11. [Pet Templates and Configuration](#pet-templates-and-configuration)
12. [GUI and Menu System](#gui-and-menu-system)
13. [Event System](#event-system)
14. [Async Operations](#async-operations)
15. [Utility Methods](#utility-methods)
16. [Examples](#examples)
17. [Best Practices](#best-practices)

## Getting Started

### Add BetterPets as a Dependency

**Maven:**
```xml
<dependency>
    <groupId>me.psikuvit</groupId>
    <artifactId>BetterPets</artifactId>
    <version>2.0</version>
    <scope>provided</scope>
</dependency>
```

**plugin.yml:**
```yaml
depend: [BetterPets]
# or
soft-depend: [BetterPets]
```

### Basic API Access

```java
import me.psikuvit.betterPets.api.PetAPI;

// Check if API is available
if (PetAPI.isInitialized()) {
    // Use the API
    Optional<Pet> activePet = PetAPI.getActivePet(player);
}
```

## API Classes Overview

### Primary Classes

| Class | Package | Description |
|-------|---------|-------------|
| `PetAPI` | `me.psikuvit.betterPets.api` | Main API interface |
| `Pet` | `me.psikuvit.betterPets.pet` | Core pet entity |
| `PlayerData` | `me.psikuvit.betterPets.data` | Player's pet data |
| `PetItem` | `me.psikuvit.betterPets.items` | Pet equipment items |
| `PetSkin` | `me.psikuvit.betterPets.pet` | Pet visual customization |
| `PetAbility` | `me.psikuvit.betterPets.abilities` | Pet abilities |
| `AutoPetRule` | `me.psikuvit.betterPets.autopet` | Auto-pet rules |
| `PetUpgrade` | `me.psikuvit.betterPets.pet` | Pet upgrade system |

### Event Classes

All events are in the `me.psikuvit.betterPets.api.events` package:

| Event | Cancellable | Description |
|-------|-------------|-------------|
| `PetEquipEvent` | ✅ | When a pet is activated |
| `PetUnequipEvent` | ✅ | When a pet is deactivated |
| `PetExpGainEvent` | ✅ | When a pet gains experience |
| `PetLevelUpEvent` | ❌ | When a pet levels up |
| `PetItemApplyEvent` | ✅ | When an item is applied to a pet |
| `PetSkinApplyEvent` | ✅ | When a skin is applied to a pet |
| `PetAbilityEvent` | ✅ | When a pet ability is used |
| `PetInteractEvent` | ✅ | When a player interacts with a pet |

## Core API Methods

### Pet Management

#### Get Player's Pets

```java
// Get all pets owned by a player
Set<Pet> pets = PetAPI.getPlayerPets(player);

// Get currently active pet
Optional<Pet> activePet = PetAPI.getActivePet(player);

// Get pet by UUID
Optional<Pet> pet = PetAPI.getPetByUUID(player, petUUID);

// Filter pets by rarity
List<Pet> legendaryPets = PetAPI.getPetsByRarity(player, Rarity.LEGENDARY);

// Filter pets by type
List<Pet> combatPets = PetAPI.getPetsByType(player, PetType.COMBAT);

// Get total pet count
int totalPets = PetAPI.getTotalPetCount(player);

// Check if player owns a specific pet
boolean owns = PetAPI.hasPlayerPet(player, pet);
```

#### Pet Operations

```java
// Give a pet to a player
Pet newPet = PetAPI.givePet(player, "golden_dragon_epic");

// Activate a pet (fires PetEquipEvent)
boolean success = PetAPI.activatePet(player, pet);

// Deactivate current pet (fires PetUnequipEvent)
boolean deactivated = PetAPI.deactivatePet(player);

// Remove a pet from a player
PetAPI.removePet(player, pet);

// Get highest level pet
Optional<Pet> highestLevel = PetAPI.getHighestLevelPet(player);

// Get rarest pet
Optional<Pet> rarest = PetAPI.getRarestPet(player);

// Get max level pet count
int maxLevelCount = PetAPI.getMaxLevelPetCount(player);
```

## Experience and Leveling

```java
// Add experience to active pet
boolean success = PetAPI.addPetExperience(player, 100, XPSource.COMBAT);

// Get experience requirements
int expNeeded = PetAPI.getExpRequiredForNextLevel(pet);

// Get level progress (0.0 to 1.0)
double progress = PetAPI.getLevelProgress(pet);

// Check if pet is max level
boolean isMax = PetAPI.isMaxLevel(pet);
```

## Pet Items and Customization

```java
// Apply an item to a pet
boolean applied = PetAPI.applyPetItem(player, pet, petItem);

// Remove item from pet
PetItem removedItem = PetAPI.removePetItem(player, pet);

// Get current equipped item
Optional<PetItem> item = PetAPI.getPetItem(pet);

// Apply a skin to a pet
boolean skinApplied = PetAPI.applySkinToPet(player, pet, skin);

// Get pet item template
Optional<PetItem> itemTemplate = PetAPI.getPetItemTemplate("speed_boost");

// Get all pet item templates
Map<String, PetItem> allItems = PetAPI.getAllPetItemTemplates();

// Get pet skin template
Optional<PetSkin> skinTemplate = PetAPI.getPetSkinTemplate("golden_dragon_skin");

// Get all skins for a pet
List<PetSkin> petSkins = PetAPI.getPetSkins("golden_dragon");
```

## XP Sharing System

```java
// Get pets being shared XP with
Set<Pet> sharedPets = PetAPI.getSharedPets(player);

// Check if player has max shared pets
boolean hasMax = PetAPI.hasMaxSharedPets(player);

// Add a pet to XP sharing
boolean added = PetAPI.addPetToXPShare(player, pet);

// Remove a pet from XP sharing
boolean removed = PetAPI.removePetFromXPShare(player, pet);
```

## AutoPet Rules

```java
// Get all AutoPet rules for a player
Set<AutoPetRule> rules = PetAPI.getAutoPetRules(player);

// Get AutoPet rule for specific trigger
Optional<AutoPetRule> rule = PetAPI.getAutoPetRule(player, TriggerType.BREAK_BLOCK);

// Check if player has AutoPet rule
boolean hasRule = PetAPI.hasAutoPetRule(player, TriggerType.PLACE_BLOCK);

// Create an AutoPet rule
Set<AutoPetException> exceptions = new HashSet<>();
exceptions.add(AutoPetException.IN_COMBAT);
boolean created = PetAPI.createAutoPetRule(player, TriggerType.BREAK_BLOCK, pet, exceptions);

// Remove AutoPet rule by trigger type
boolean removed = PetAPI.removeAutoPetRule(player, TriggerType.BREAK_BLOCK);

// Remove AutoPet rule by UUID
boolean removed = PetAPI.removeAutoPetRule(player, ruleUUID);
```

## Pet Upgrade System

```java
// Get pet currently being upgraded
Optional<Pet> upgradingPet = PetAPI.getUpgradingPet(player);

// Check if player has upgrading pet
boolean hasUpgrading = PetAPI.hasUpgradingPet(player);

// Start upgrading a pet
boolean started = PetAPI.startPetUpgrade(player, pet);

// Complete a pet upgrade
boolean completed = PetAPI.completePetUpgrade(player);

// Cancel a pet upgrade
boolean cancelled = PetAPI.cancelPetUpgrade(player);

// Get remaining upgrade time
long remainingTime = PetAPI.getUpgradeRemainingTime(pet);
```

## Pet Abilities

```java
// Get all abilities for a pet
List<PetAbility> abilities = PetAPI.getPetAbilities(pet);

// Get unlocked abilities
List<PetAbility> unlocked = PetAPI.getUnlockedAbilities(pet);

// Execute a pet ability
boolean executed = PetAPI.executePetAbility(player, pet, ability);
```

## Pet Templates and Configuration

```java
// Get pet template by ID
Optional<Pet> template = PetAPI.getPetTemplate("golden_dragon_epic");

// Get all available pet templates
Map<String, Pet> allTemplates = PetAPI.getAllPetTemplates();
```

## GUI and Menu System

```java
// Open pet management GUI
PetAPI.openPetGUI(player, 0); // Page 0

// Open pet upgrade GUI
PetAPI.openUpgradeGUI(player);

// Open AutoPet rules GUI
PetAPI.openAutoPetRulesGUI(player);
```

## Visibility and Sorting

```java
// Get pet visibility type
VisibilityType visibility = PetAPI.getPetVisibilityType(player);

// Set pet visibility type
PetAPI.setPetVisibilityType(player, VisibilityType.OWN);

// Get pet sort type
SortType sortType = PetAPI.getPetSort(player);

// Set pet sort type
PetAPI.setPetSort(player, SortType.ALPHABETICAL);
```

## Event System

### Listening to Events

```java
@EventHandler
public void onPetLevelUp(PetLevelUpEvent event) {
    Pet pet = event.getPet();
    Player owner = event.getOwner();
    int newLevel = event.getNewLevel();
    
    // Handle pet level up
    owner.sendMessage("Your " + pet.getName() + " reached level " + newLevel + "!");
}

@EventHandler
public void onPetEquip(PetEquipEvent event) {
    Pet pet = event.getPet();
    Player player = event.getPlayer();
    
    // Handle pet equipment
    if (event.isCancelled()) {
        player.sendMessage("Pet equipment was cancelled!");
    }
}

@EventHandler
public void onPetAbility(PetAbilityEvent event) {
    Pet pet = event.getPet();
    PetAbility ability = event.getAbility();
    Player player = event.getPlayer();
    
    // Handle ability usage
    if (event.isCancelled()) {
        player.sendMessage("Ability usage was cancelled!");
    }
}
```

## Async Operations

```java
// Asynchronously load player data
CompletableFuture<Optional<PlayerData>> future = PetAPI.loadPlayerDataAsync(player);
future.thenAccept(data -> {
    if (data.isPresent()) {
        // Handle loaded data
    }
});

// Asynchronously save player data
CompletableFuture<Void> saveFuture = PetAPI.savePlayerDataAsync(player);
saveFuture.thenRun(() -> {
    // Handle save completion
});
```

## Utility Methods

```java
// Check if API is initialized
boolean initialized = PetAPI.isInitialized();

// Get plugin instance (use with caution)
Main plugin = PetAPI.getPlugin();

// Get all active pets for all online players
Map<Player, Pet> allActivePets = PetAPI.getAllActivePets();

// Get active pets by type
List<Pet> activeCombatPets = PetAPI.getActivePetsByType(PetType.COMBAT);

// Get active pets by rarity
List<Pet> activeLegendaryPets = PetAPI.getActivePetsByRarity(Rarity.LEGENDARY);
```

## Examples

### Complete Pet Management System

```java
public class PetManager {
    
    public void giveAndActivatePet(Player player, String petId) {
        // Give the pet
        Pet pet = PetAPI.givePet(player, petId);
        if (pet == null) {
            player.sendMessage("Failed to give pet!");
            return;
        }
        
        // Activate the pet
        if (PetAPI.activatePet(player, pet)) {
            player.sendMessage("Pet activated successfully!");
        } else {
            player.sendMessage("Failed to activate pet!");
        }
    }
    
    public void setupAutoPetRule(Player player, Pet pet) {
        // Create AutoPet rule for mining
        Set<AutoPetException> exceptions = new HashSet<>();
        exceptions.add(AutoPetException.IN_COMBAT);
        
        if (PetAPI.createAutoPetRule(player, TriggerType.BREAK_BLOCK, pet, exceptions)) {
            player.sendMessage("AutoPet rule created!");
        }
    }
    
    public void upgradePet(Player player, Pet pet) {
        if (pet.getUpgrade() == null) {
            player.sendMessage("This pet cannot be upgraded!");
            return;
        }
        
        if (PetAPI.startPetUpgrade(player, pet)) {
            player.sendMessage("Pet upgrade started!");
        } else {
            player.sendMessage("Failed to start upgrade!");
        }
    }
}
```

### Custom Ability System

```java
public class CustomAbilityManager {
    
    @EventHandler
    public void onPetAbility(PetAbilityEvent event) {
        Player player = event.getPlayer();
        Pet pet = event.getPet();
        PetAbility ability = event.getAbility();
        
        // Handle custom abilities
        switch (ability.name()) {
            case "teleport_home":
                if (canUseTeleport(player)) {
                    teleportToHome(player);
                } else {
                    event.setCancelled(true);
                    player.sendMessage("Teleport is on cooldown!");
                }
                break;
                
            case "mining_speed":
                applyMiningSpeedBoost(player, pet.getLevel());
                break;
                
            case "combat_aura":
                applyAreaEffectBuff(player.getLocation(), pet.getLevel());
                break;
        }
    }
    
    private void applyMiningSpeedBoost(Player player, int petLevel) {
        // Apply temporary mining speed based on pet level
        int duration = 30 + (petLevel * 2); // seconds
        PotionEffect effect = new PotionEffect(
            PotionEffectType.HASTE, 
            duration * 20, 
            Math.min(petLevel / 20, 2) // Max level 2
        );
        player.addPotionEffect(effect);
    }
}
```

### XP Sharing Management

```java
public class XPShareManager {
    
    public void setupXPSharing(Player player, Pet pet) {
        if (PetAPI.hasMaxSharedPets(player)) {
            player.sendMessage("You already have the maximum number of pets sharing XP!");
            return;
        }
        
        if (PetAPI.addPetToXPShare(player, pet)) {
            player.sendMessage("Pet added to XP sharing!");
        } else {
            player.sendMessage("Failed to add pet to XP sharing!");
        }
    }
    
    public void removeFromXPSharing(Player player, Pet pet) {
        if (PetAPI.removePetFromXPShare(player, pet)) {
            player.sendMessage("Pet removed from XP sharing!");
        } else {
            player.sendMessage("Pet was not sharing XP!");
        }
    }
}
```

## Best Practices

### 1. Always Check API Availability

```java
if (!PetAPI.isInitialized()) {
    getLogger().warning("BetterPets API not available!");
    return;
}
```

### 2. Handle Optionals Properly

```java
// Good
Optional<Pet> activePet = PetAPI.getActivePet(player);
if (activePet.isPresent()) {
    Pet pet = activePet.get();
    // Use pet safely
}

// Better
PetAPI.getActivePet(player).ifPresent(pet -> {
    // Use pet safely
});
```

### 3. Use Async Operations for Heavy Tasks

```java
// For database operations
PetAPI.loadPlayerDataAsync(player).thenAccept(data -> {
    if (data.isPresent()) {
        // Handle data on main thread
        Bukkit.getScheduler().runTask(plugin, () -> {
            // Update UI or perform actions
        });
    }
});
```

### 4. Handle Events Properly

```java
@EventHandler(priority = EventPriority.NORMAL)
public void onPetLevelUp(PetLevelUpEvent event) {
    // This event is NOT cancellable
    Pet pet = event.getPet();
    Player owner = event.getOwner();
    
    // Perform actions after level up
    owner.sendMessage("Congratulations! Your " + pet.getName() + " leveled up!");
}

@EventHandler(priority = EventPriority.NORMAL)
public void onPetEquip(PetEquipEvent event) {
    // This event IS cancellable
    if (someCondition) {
        event.setCancelled(true);
        event.getPlayer().sendMessage("Pet equipment was cancelled!");
    }
}
```

### 5. Use Proper Error Handling

```java
public void safePetOperation(Player player, Pet pet) {
    try {
        if (PetAPI.activatePet(player, pet)) {
            player.sendMessage("Pet activated!");
        } else {
            player.sendMessage("Failed to activate pet!");
        }
    } catch (Exception e) {
        getLogger().severe("Error during pet activation: " + e.getMessage());
        player.sendMessage("An error occurred while activating your pet!");
    }
}
```

### 6. Cache Frequently Used Data

```java
public class PetCache {
    private final Map<UUID, Optional<Pet>> activePetCache = new HashMap<>();
    
    public Optional<Pet> getActivePet(Player player) {
        return activePetCache.computeIfAbsent(
            player.getUniqueId(), 
            uuid -> PetAPI.getActivePet(player)
        );
    }
    
    public void invalidateCache(Player player) {
        activePetCache.remove(player.getUniqueId());
    }
}
```

## Version History

### v2.0 (Current)
- Added comprehensive AutoPet rules system
- Added pet upgrade system
- Added pet abilities management
- Added GUI and menu system
- Added extensive utility methods
- Added async operations support
- Improved error handling and null safety
- Added comprehensive documentation

### v1.0
- Basic pet management
- Experience and leveling
- Pet items and customization
- XP sharing system
- Basic event system

## Support

- **Documentation**: This API documentation
- **Issues**: Report bugs via GitHub Issues
- **Discussions**: Join our Discord for community support

---

**BetterPets API v2.0** - Comprehensive pet system integration for Minecraft plugins! 🐾
