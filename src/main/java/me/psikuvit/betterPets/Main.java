package me.psikuvit.betterPets;

import me.psikuvit.betterPets.abilities.MountManager;
import me.psikuvit.betterPets.api.PetAPI;
import me.psikuvit.betterPets.autopet.AutoPetRuleService;
import me.psikuvit.betterPets.commands.PetCommand;
import me.psikuvit.betterPets.config.PetItemLoader;
import me.psikuvit.betterPets.config.PetSkinLoader;
import me.psikuvit.betterPets.database.DatabaseManager;
import me.psikuvit.betterPets.hooks.PAPIHook;
import me.psikuvit.betterPets.hooks.WorldGuardHook;
import me.psikuvit.betterPets.listeners.actions.RulesListener;
import me.psikuvit.betterPets.listeners.actions.SkillsListener;
import me.psikuvit.betterPets.listeners.gui.AutoPetListener;
import me.psikuvit.betterPets.listeners.gui.EXPShareListener;
import me.psikuvit.betterPets.listeners.gui.MenuListener;
import me.psikuvit.betterPets.listeners.gui.PetSelectionListener;
import me.psikuvit.betterPets.listeners.gui.UpgradeMenuListener;
import me.psikuvit.betterPets.listeners.pet.PetItemProtectionListener;
import me.psikuvit.betterPets.listeners.pet.PetRightClickListener;
import me.psikuvit.betterPets.listeners.player.AbilityListener;
import me.psikuvit.betterPets.listeners.player.BlockPlaceListener;
import me.psikuvit.betterPets.listeners.player.PetInteractItemListener;
import me.psikuvit.betterPets.listeners.player.PlayerListener;
import me.psikuvit.betterPets.registry.PetManager;
import me.psikuvit.betterPets.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    private PetManager registryPetManager;
    private PetSkinLoader petSkinLoader;
    private PetItemLoader petItemLoader;
    private PlayerPetManager petManager;
    private AutoPetRuleService autoPetRuleService;
    private static Main instance;
    private PAPIHook placeholders;
    private WorldGuardHook worldGuardHook;
    private DatabaseManager databaseManager;
    private MountManager mountManager;

    @Override
    public void onEnable() {
        instance = this;

        try {
            // Save default configuration
            saveDefaultConfig();
            getLogger().info("Configuration loaded successfully");

            // Initialize core components
            getLogger().info("Initializing core components...");
            registryPetManager = new PetManager();
            petSkinLoader = new PetSkinLoader(this);
            petItemLoader = new PetItemLoader(this);
            databaseManager = new DatabaseManager(this);
            petManager = new PlayerPetManager(this);
            autoPetRuleService = new AutoPetRuleService(this);
            mountManager = new MountManager();

            // Initialize API and Messages
            PetAPI.init(this);
            Messages.load(this);
            getLogger().info("API and messaging system initialized");

            // Initialize pets using registry system
            getLogger().info("Initializing pet registry...");
            registryPetManager.initializePets();
            getLogger().info(registryPetManager.getRegistryInfo());

            // Load configuration data  
            getLogger().info("Loading configuration data...");
            petSkinLoader.loadSkins();
            petItemLoader.loadItems();

            // Initialize database
            databaseManager.initialize();

            // PlaceholderAPI integration

            setupPlaceholderAPI();
            setupWorldGuard();

            // Register event listeners
            getLogger().info("Registering event listeners...");
            registerListener();

            // Register commands
            getLogger().info("Registering commands...");
            PetCommand petCommand = new PetCommand(this);
            this.getServer().getCommandMap().register("betterpets", petCommand);

            getLogger().info("BetterPets has been successfully enabled!");
        } catch (Exception e) {
            getLogger().severe("Failed to enable BetterPets: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling BetterPets...");

        try {
            // Remove all active pets
            if (petManager != null) {
                getLogger().info("Deactivating all active pets...");
                int deactivatedCount = 0;
                for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
                    if (petManager.getActivePet(player) != null) {
                        petManager.deactivatePet(player);
                        deactivatedCount++;
                    }
                }
                getLogger().info("Deactivated " + deactivatedCount + " active pets");
                petManager.cleanup();
            }


            getLogger().info("BetterPets has been disabled successfully");
        } catch (Exception e) {
            getLogger().severe("Error occurred during plugin shutdown: " + e.getMessage());
        }
    }

    public void registerListener() {
        new SkillsListener(this);
        new MenuListener(this);
        new PlayerListener(this);
        new PetInteractItemListener(this);
        new AbilityListener(this);
        new PetRightClickListener(this);
        new BlockPlaceListener(this);
        new EXPShareListener(this);
        new PetItemProtectionListener(this);
        new UpgradeMenuListener(this);
        new PetSelectionListener(this);
        new AutoPetListener(this);
        new RulesListener(this);

    }

    public PetManager getRegistryPetManager() {
        return registryPetManager;
    }

    /**
     * Get the pet manager for registry-based pet operations
     *
     * @return The pet manager instance
     */
    public PlayerPetManager getPetManager() {
        return petManager;
    }

    public PetSkinLoader getPetSkinLoader() {
        return petSkinLoader;
    }

    public static Main getInstance() {
        return instance;
    }

    public PetItemLoader getPetItemLoader() {
        return petItemLoader;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public AutoPetRuleService getAutoPetRuleService() {
        return autoPetRuleService;
    }

    public MountManager getMountManager() {
        return mountManager;
    }

    public WorldGuardHook getWorldGuardHook() {
        return worldGuardHook;
    }

    private void setupPlaceholderAPI() {
        try {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                placeholders = new PAPIHook(this);
                placeholders.register();
            }
            getLogger().info("Successfully hooked into PlaceholderAPI!");
        } catch (Exception e) {
            getLogger().warning("Failed to initialize PlaceholderAPI integration: " + e.getMessage());
        }
    }

    private void setupWorldGuard() {
        try {
            if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
                worldGuardHook = new WorldGuardHook();
                worldGuardHook.setupWorldGuard(this);
            }
            getLogger().info("Successfully hooked into WorldGuard!");
        } catch (Exception e) {
            getLogger().warning("Failed to initialize WorldGuard integration: " + e.getMessage());
        }
    }
}
