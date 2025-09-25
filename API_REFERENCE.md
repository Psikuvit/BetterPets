# BetterPets API Quick Reference

## Import Statement
```java
import me.psikuvit.betterPets.api.PetAPI;
```

## Essential Methods

### Pet Management
```java
// Get player's active pet
Optional<Pet> activePet = PetAPI.getActivePet(player);

// Get all player's pets
Set<Pet> pets = PetAPI.getPlayerPets(player);

// Activate a pet
boolean success = PetAPI.activatePet(player, pet);

// Deactivate current pet
boolean deactivated = PetAPI.deactivatePet(player);

// Give pet to player
Pet newPet = PetAPI.givePet(player, "golden_dragon_epic");

// Check if player owns pet
boolean owns = PetAPI.hasPlayerPet(player, pet);
```

### Experience System
```java
// Add experience to active pet
PetAPI.addPetExperience(player, 100, XPSource.COMBAT);

// Get exp needed for next level
int expNeeded = PetAPI.getExpRequiredForNextLevel(pet);

// Get level progress (0.0 to 1.0)
double progress = PetAPI.getLevelProgress(pet);

// Check if max level
boolean isMax = PetAPI.isMaxLevel(pet);
```

### Pet Items & Customization
```java
// Apply item to pet
boolean applied = PetAPI.applyPetItem(player, pet, item);

// Remove pet item
PetItem removed = PetAPI.removePetItem(player, pet);

// Apply skin to pet
boolean skinApplied = PetAPI.applySkinToPet(player, pet, skin);

// Get equipped item
Optional<PetItem> item = PetAPI.getPetItem(pet);
```

### XP Sharing
```java
// Get shared pets
Set<Pet> sharedPets = PetAPI.getSharedPets(player);

// Add to XP sharing (max 4)
boolean added = PetAPI.addPetToXPShare(player, pet);

// Remove from XP sharing
boolean removed = PetAPI.removePetFromXPShare(player, pet);

// Check if at max
boolean atMax = PetAPI.hasMaxSharedPets(player);
```

### Filtering & Searching
```java
// Get pets by rarity
List<Pet> legendary = PetAPI.getPetsByRarity(player, Rarity.LEGENDARY);

// Get pets by type
List<Pet> combat = PetAPI.getPetsByType(player, PetType.COMBAT);

// Find pet by UUID
Optional<Pet> pet = PetAPI.getPetByUUID(player, petUUID);

// Get total pet count
int total = PetAPI.getTotalPetCount(player);
```

### Configuration Access
```java
// Get pet template
Optional<Pet> template = PetAPI.getPetTemplate("golden_dragon_epic");

// Get all templates
Map<String, Pet> all = PetAPI.getAllPetTemplates();
```

### Async Operations
```java
// Load player data async
CompletableFuture<Optional<PlayerData>> future = PetAPI.loadPlayerDataAsync(player);

// Save player data async
CompletableFuture<Void> save = PetAPI.savePlayerDataAsync(player);
```

## Event Listeners

### Basic Event Handling
```java
@EventHandler
public void onPetEquip(PetEquipEvent event) {
    Player player = event.getPlayer();
    Pet pet = event.getPet();
    // Handle pet equipping
}

@EventHandler
public void onPetLevelUp(PetLevelUpEvent event) {
    Pet pet = event.getPet();
    int newLevel = event.getNewLevel();
    // Handle level up
}

@EventHandler
public void onExpGain(PetExpGainEvent event) {
    // Modify experience gain
    event.setAmount(event.getAmount() * 2);
}
```

### Cancellable Events
```java
@EventHandler
public void onPetEquip(PetEquipEvent event) {
    if (someCondition) {
        event.setCancelled(true);
        event.getPlayer().sendMessage("Cannot equip pet!");
    }
}
```

## Common Patterns

### Check API Availability
```java
if (!PetAPI.isInitialized()) {
    getLogger().warning("BetterPets not available!");
    return;
}
```

### Safe Optional Handling
```java
PetAPI.getActivePet(player).ifPresent(pet -> {
    // Use pet safely
    pet.getAbilities().forEach(ability -> {
        // Process abilities
    });
});
```

### Error Handling
```java
try {
    boolean success = PetAPI.activatePet(player, pet);
    if (!success) {
        player.sendMessage("Failed to activate pet!");
    }
} catch (Exception e) {
    getLogger().severe("Pet activation error: " + e.getMessage());
}
```

### Event Priority
```java
@EventHandler(priority = EventPriority.HIGH)
public void onPetEquip(PetEquipEvent event) {
    // Handle with high priority
}
```

## Enums Reference

### XPSource
- `COMBAT` - From fighting entities
- `MINING` - From mining blocks
- `FARMING` - From farming activities
- `FISHING` - From fishing
- `ENCHANTING` - From enchanting items
- `EXP_SHARE` - From XP sharing system

### Rarity
- `COMMON` - Most basic pets
- `RARE` - Uncommon pets with better stats
- `EPIC` - Rare pets with special abilities
- `LEGENDARY` - Very rare pets with powerful abilities
- `MYTHIC` - Extremely rare pets with unique abilities

### PetType
- `COMBAT` - Combat-focused pets
- `MINING` - Mining-focused pets
- `FARMING` - Farming-focused pets
- `FISHING` - Fishing-focused pets
- `UNKNOWN` - Default/undefined type

### SortType
- `DEFAULT` - Default sorting
- `LEVEL` - Sort by level
- `RARITY` - Sort by rarity
- `TYPE` - Sort by pet type
- `ALPHABETICAL` - Sort alphabetically

### VisibilityType
- `ALL` - Show all pets
- `OWN` - Show only own pets
- `NONE` - Hide all pets

## Quick Examples

### Pet Shop Integration
```java
public void buyPet(Player player, String petId, int cost) {
    if (canAfford(player, cost) && !PetAPI.getPetTemplate(petId).isEmpty()) {
        Pet pet = PetAPI.givePet(player, petId);
        if (pet != null) {
            chargeMoney(player, cost);
            player.sendMessage("Pet purchased!");
        }
    }
}
```

### Combat Integration
```java
@EventHandler
public void onEntityDeath(EntityDeathEvent event) {
    Player killer = event.getEntity().getKiller();
    if (killer != null) {
        PetAPI.addPetExperience(killer, 25, XPSource.COMBAT);
    }
}
```

### Statistics Tracking
```java
public void showPetStats(Player player) {
    int total = PetAPI.getTotalPetCount(player);
    long maxLevel = PetAPI.getPlayerPets(player).stream()
        .mapToInt(Pet::getLevel)
        .max().orElse(0);
    
    player.sendMessage("Pets: " + total + ", Max Level: " + maxLevel);
}
```
