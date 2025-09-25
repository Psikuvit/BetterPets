# BetterPets

A comprehensive pet management system for Minecraft servers running on Paper/Spigot. Inspired by Hypixel's pet system, BetterPets provides an advanced, customizable pet experience with leveling, abilities, and visual customization.

## Features

### 🐾 Core Pet System
- **Pet Types**: Combat, Mining, Farming, and more specialized pet types
- **Rarity System**: COMMON, RARE, EPIC, LEGENDARY, MYTHIC rarities
- **Level Progression**: Pets gain experience and level up with scaling requirements
- **Pet Abilities**: Passive and active abilities that unlock based on rarity and level

### 🎨 Customization
- **Pet Skins**: Visual customization with custom texture support
- **Pet Items**: Equipment system to enhance pet capabilities
- **Attribute System**: Health, Damage, Speed, and other customizable stats

### 📊 Advanced Features
- **EXP Sharing**: Share experience with up to 4 pets simultaneously
- **Pet Upgrades**: Time-based pet tier upgrading system
- **Multiple Database Support**: SQLite, MySQL, and MongoDB support
- **Visibility Controls**: Control pet visibility (All/Own/None)

### 🔧 Developer Features
- **Comprehensive API**: Event system and hooks for other plugins
- **Extensible Architecture**: Easy to add new pet types, abilities, and items
- **Multi-database Support**: Choose your preferred storage solution

## Requirements

- **Minecraft Version**: 1.21.4+ (Paper recommended)
- **Java Version**: 21+
- **Optional Dependencies**:
  - PlaceholderAPI (for placeholder support)
  - EcoSkills (for skill integration)

## Installation

1. Download the latest release from the [Releases](https://github.com/Psikuvit/BetterPets/releases) page
2. Place the jar file in your server's `plugins` folder
3. Start/restart your server
4. Configure the plugin in `plugins/BetterPets/config.yml`
5. Restart the server to apply configuration changes

## Configuration

### Database Configuration

```yaml
database:
  type: sqlite  # Options: sqlite, mysql
  
  mysql:
    host: localhost
    port: 3306
    database: betterpets
    username: root
    password: ""
```

### Debug Mode
```yaml
debug: false  # Set to true for detailed logging
```

## Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/pet` | `betterpets.use` | Opens the pet management GUI |
| `/pet give <player> <pet_id>` | `betterpets.admin` | Give a pet to a player |
| `/pet reload` | `betterpets.admin` | Reload plugin configuration |

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `betterpets.use` | Basic pet system usage | All players |
| `betterpets.admin` | Administrative commands | Operators |
| `betterpets.bypass` | Bypass certain restrictions | Operators |

## Pet Configuration

Pets are configured in YAML files located in `plugins/BetterPets/pets/`. Here's an example:

```yaml
id: golden_dragon
name: "Golden Dragon"
type: COMBAT
rarities:
  - COMMON
  - RARE
  - EPIC
  - LEGENDARY
  - MYTHIC
texture-url: "http://textures.minecraft.net/texture/..."

abilities:
  speed_boost:
    name: "Speed Boost"
    description: "Increases movement speed"
    class: "me.psikuvit.betterPets.abilities.golden_dragon.SpeedBoostAbility"
    required-rarity: RARE
    
  strength_aura:
    name: "Strength Aura"
    description: "Provides strength to nearby players"
    class: "me.psikuvit.betterPets.abilities.golden_dragon.StrengthAuraAbility"
    required-rarity: EPIC

attributes:
  health:
    base-value: 2.0
    per-level-increase: 0.5
  damage:
    base-value: 1.0
    per-level-increase: 0.2

upgrades:
  RARE:
    duration: "24h"
    cost: 1000
    items:
      - GOLD_INGOT
      - DIAMOND
```

## API Usage

### Events

BetterPets provides several custom events you can listen to:

```java
@EventHandler
public void onPetLevelUp(PetLevelUpEvent event) {
    Pet pet = event.getPet();
    Player owner = event.getOwner();
    int newLevel = event.getNewLevel();
    
    // Handle pet level up
}

@EventHandler
public void onPetEquip(PetEquipEvent event) {
    Pet pet = event.getPet();
    Player player = event.getPlayer();
    
    // Handle pet equipment
}
```

### Getting Player's Active Pet

```java
PlayerPetManager petManager = Main.getInstance().getPetManager();
Pet activePet = petManager.getActivePet(player);

if (activePet != null) {
    // Player has an active pet
    String petName = activePet.getName();
    int petLevel = activePet.getLevel();
}
```

### Creating Custom Abilities

```java
public class MyCustomAbility implements IAbility {
    @Override
    public void execute(Player owner) {
        // Active ability execution
    }
    
    @Override
    public void handleEvent(Event event, Player owner) {
        // Handle Bukkit events for passive abilities
    }
    
    @Override
    public boolean isPassive() {
        return true; // or false for active abilities
    }
}
```

## Architecture Overview

### Core Classes
- **Pet**: Main pet entity with stats, abilities, and visual representation
- **PlayerPetManager**: Manages pets for each player
- **PetLoader**: Handles loading pets from configuration files
- **DatabaseManager**: Abstracts database operations across different providers

### Design Patterns Used
- **Builder Pattern**: For constructing abilities and complex objects
- **Strategy Pattern**: For different database implementations
- **Observer Pattern**: For the event system
- **Factory Pattern**: For creating pets and abilities

## Development

### Building from Source

1. Clone the repository
2. Ensure you have Java 21+ and Maven installed
3. Run `mvn clean package`
4. The compiled jar will be in the `target` directory

### Running Tests

```bash
mvn test
```

### Code Quality

The project follows these principles:
- Comprehensive error handling and logging
- Null safety checks throughout
- Async operations for heavy tasks
- Proper resource cleanup

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style Guidelines

- Use proper JavaDoc for public methods
- Follow existing naming conventions
- Add unit tests for new functionality
- Ensure proper error handling

## Troubleshooting

### Common Issues

**Database Connection Errors**
- Check database credentials in config.yml
- Ensure database server is running
- Verify network connectivity

**Pet Not Spawning**
- Check server logs for errors
- Verify pet configuration files are valid
- Ensure player has sufficient permissions

**Performance Issues**
- Enable debug logging to identify bottlenecks
- Check database query performance
- Monitor memory usage with large numbers of pets

### Debug Mode

Enable debug mode in config.yml for detailed logging:
```yaml
debug: true
```

This will provide extensive logging information to help diagnose issues.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by Hypixel's pet system
- Built on the Paper/Spigot API
- Uses Adventure API for modern text components
- Thanks to the Minecraft plugin development community

## Support

- **Documentation**: Check this README and in-code JavaDoc
- **Issues**: Report bugs via [GitHub Issues](https://github.com/your-repo/BetterPets/issues)
- **Discussions**: Join our [Discord](https://discord.gg/your-server) for community support

---

**BetterPets** - Making pet companions better, one block at a time! 🐾
