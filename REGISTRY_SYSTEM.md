# BetterPets Registry System

## Overview

BetterPets has been transformed from a configuration-based system to a registry-based system that provides much more control and flexibility for pet creation and management.

## Key Changes

### 1. Abstract Pet Base Class
- `Pet` is now an **abstract class** that must be extended
- Each pet type has its own dedicated class (e.g., `GoldenDragonPet`, `CatPet`)
- Pet-specific behavior is implemented directly in the pet class

### 2. Registry System
- `PetRegistry` manages all pet types
- `PetManager` provides a high-level interface for pet operations
- No more YAML configuration files for pets
- Pets are registered programmatically

### 3. Benefits
- **Full Control**: Complete control over each pet's behavior
- **Type Safety**: Compile-time checking of pet implementations  
- **Custom Logic**: Each pet can have unique level-up behavior, abilities, etc.
- **Extensibility**: Easy to add new pets without configuration
- **Performance**: No file I/O during runtime pet creation

## Creating a New Pet

### Step 1: Create Pet Class
```java
public class DogPet extends Pet {
    
    public DogPet(Rarity rarity) {
        super("dog_" + rarity.name().toLowerCase(), 
              "Dog", 
              PetType.COMBAT, 
              rarity, 
              "http://your-texture-url");
    }
    
    public DogPet() {
        this(Rarity.COMMON); // Default rarity
    }
    
    @Override
    protected List<PetAbility> initializeAbilities() {
        // Define your pet's abilities
        return new ArrayList<>();
    }
    
    @Override
    protected PetAttribute[] initializeAttributes() {
        // Define your pet's stat bonuses
        return new PetAttribute[0];
    }
    
    @Override
    protected PetUpgrade initializeUpgrade() {
        // Define upgrade requirements (can return null)
        return null;
    }
    
    @Override
    public Rarity getDefaultRarity() {
        return Rarity.COMMON;
    }
    
    @Override
    public Rarity[] getAvailableRarities() {
        return new Rarity[]{Rarity.COMMON, Rarity.RARE, Rarity.EPIC};
    }
    
    @Override
    protected void onActivate(Player player) {
        // Custom behavior when pet is equipped
        player.sendMessage("§6Your loyal dog companion joins you!");
    }
    
    @Override
    protected void onLevelUp(int newLevel) {
        // Custom behavior when pet levels up
        if (owner != null && newLevel == 50) {
            owner.sendMessage("§6Your dog has become incredibly loyal!");
        }
    }
}
```

### Step 2: Register Pet
In `PetManager.initializePets()`:
```java
petRegistry.registerPet("dog",
        DogPet::new,
        new Rarity[]{Rarity.COMMON, Rarity.RARE, Rarity.EPIC});
```

## Example Pets

### Golden Dragon Pet
- **Type**: Combat
- **Rarities**: Legendary, Mythic  
- **Abilities**: Speed Boost, Strength Aura
- **Special**: Custom milestone level-up messages

### Cat Pet  
- **Type**: Mining
- **Rarities**: All rarities available
- **Abilities**: Night Vision, Stealth, Nine Lives (passive)
- **Special**: Rarity-based attribute scaling

## Legacy Compatibility

The system maintains backward compatibility through:
- `LegacyPetLoaderWrapper` - wraps the old `PetLoader` interface
- Existing code using `Main.getInstance().getPetLoader()` continues to work
- Database serialization/deserialization unchanged

## API Usage

### Getting a Pet
```java
// Using the registry directly
PetManager petManager = Main.getInstance().getRegistryPetManager();
Pet pet = petManager.getPet("cat", Rarity.LEGENDARY);

// Legacy method (still works)
Pet pet2 = Main.getInstance().getPetLoader().getPet("cat_legendary");
```

### Registering Custom Pets
```java
PetManager petManager = Main.getInstance().getRegistryPetManager();
petManager.registerCustomPet("custom_pet", 
                             () -> new CustomPet(), 
                             new Rarity[]{Rarity.RARE});
```

### Registry Information
```java
PetManager petManager = Main.getInstance().getRegistryPetManager();
System.out.println(petManager.getRegistryInfo());
// Output: Registry Info: 2 pet types, 8 total variants
```

## Migration Notes

- **Configuration files**: Pet YAML files are no longer used
- **Abilities**: Now defined programmatically in pet classes
- **Attributes**: Defined in `initializeAttributes()` method
- **Upgrades**: Defined in `initializeUpgrade()` method
- **Custom behavior**: Use `onActivate()`, `onDeactivate()`, `onLevelUp()` methods

## Advanced Features

### Dynamic Pet Creation
Pets can have complex initialization logic:
```java
@Override
private List<PetAbility> initializeAbilities() {
    List<PetAbility> abilities = new ArrayList<>();
    
    // Different abilities based on rarity
    if (getRarity().ordinal() >= Rarity.RARE.ordinal()) {
        abilities.add(createStealthAbility());
    }
    
    if (getRarity() == Rarity.MYTHIC) {
        abilities.add(createUltimateAbility());
    }
    
    return abilities;
}
```

### Event-Based Abilities
```java
IAbility passiveAbility = new IAbility() {
    @Override
    public boolean handleEvent(Event event, Player owner) {
        // Prevent death and restore health
        return event instanceof PlayerDeathEvent; // Event handled
    }
    
    
};
```

This new system provides complete control over pet behavior while maintaining compatibility with existing functionality.
