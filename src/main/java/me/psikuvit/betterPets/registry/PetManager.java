package me.psikuvit.betterPets.registry;

import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.pets.alchemy.ParrotPet;
import me.psikuvit.betterPets.pets.alchemy.SheepPet;
import me.psikuvit.betterPets.pets.combat.EnderDragonPet;
import me.psikuvit.betterPets.pets.combat.EndermanPet;
import me.psikuvit.betterPets.pets.combat.EndermitePet;
import me.psikuvit.betterPets.pets.combat.GhoulPet;
import me.psikuvit.betterPets.pets.combat.GolemPet;
import me.psikuvit.betterPets.pets.combat.GrandmaWolfPet;
import me.psikuvit.betterPets.pets.combat.HorsePet;
import me.psikuvit.betterPets.pets.combat.HoundPet;
import me.psikuvit.betterPets.pets.combat.LionPet;
import me.psikuvit.betterPets.pets.combat.PhoenixPet;
import me.psikuvit.betterPets.pets.combat.PigmanPet;
import me.psikuvit.betterPets.pets.combat.RockPet;
import me.psikuvit.betterPets.pets.combat.SkeletonHorsePet;
import me.psikuvit.betterPets.pets.combat.SkeletonPet;
import me.psikuvit.betterPets.pets.combat.SpiderPet;
import me.psikuvit.betterPets.pets.combat.TarantulaPet;
import me.psikuvit.betterPets.pets.combat.TigerPet;
import me.psikuvit.betterPets.pets.combat.WolfPet;
import me.psikuvit.betterPets.pets.combat.ZombiePet;
import me.psikuvit.betterPets.pets.enchanting.GuardianPet;
import me.psikuvit.betterPets.pets.farming.BeePet;
import me.psikuvit.betterPets.pets.farming.ChickenPet;
import me.psikuvit.betterPets.pets.farming.ElephantPet;
import me.psikuvit.betterPets.pets.farming.PigPet;
import me.psikuvit.betterPets.pets.farming.RabbitPet;
import me.psikuvit.betterPets.pets.fishing.BlueWhalePet;
import me.psikuvit.betterPets.pets.fishing.DolphinPet;
import me.psikuvit.betterPets.pets.fishing.FlyingFishPet;
import me.psikuvit.betterPets.pets.fishing.SquidPet;
import me.psikuvit.betterPets.pets.foraging.GiraffePet;
import me.psikuvit.betterPets.pets.foraging.MonkeyPet;
import me.psikuvit.betterPets.pets.foraging.OcelotPet;
import me.psikuvit.betterPets.pets.mining.SilverfishPet;
import me.psikuvit.betterPets.pets.mining.WitherSkeletonPet;
import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.enums.Rarity;

import java.util.Map;
import java.util.Set;

public class PetManager {

    private final PetRegistry petRegistry;

    public PetManager() {
        this.petRegistry = new PetRegistry();
    }

    public void initializePets() {
        Messages.debug("Initializing pet registry system...");

        try {
            // Register combat pets
            petRegistry.registerPet("ender_dragon", EnderDragonPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("enderman", EndermanPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY, Rarity.MYTHIC);
            petRegistry.registerPet("endermite", EndermitePet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY, Rarity.MYTHIC);
            petRegistry.registerPet("ghoul", GhoulPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("golem", GolemPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("grandma_wolf", GrandmaWolfPet::new, Rarity.LEGENDARY);
            petRegistry.registerPet("horse", HorsePet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("hound", HoundPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("lion", LionPet::new, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("phoenix", PhoenixPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("pigman", PigmanPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("rock", RockPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("skeleton_horse", SkeletonHorsePet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("skeleton", SkeletonPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("spider", SpiderPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("tarantula", TarantulaPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("tiger", TigerPet::new, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("wolf", WolfPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("zombie", ZombiePet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);

            // Register farming pets
            petRegistry.registerPet("bee", BeePet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("chicken", ChickenPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("elephant", ElephantPet::new, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("pig", PigPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("rabbit", RabbitPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);

            // Register fishing pets
            petRegistry.registerPet("blue_whale", BlueWhalePet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("dolphin", DolphinPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("flying_fish", FlyingFishPet::new, Rarity.RARE, Rarity.EPIC);
            petRegistry.registerPet("squid", SquidPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);

            // Register mining pets
            petRegistry.registerPet("silverfish", SilverfishPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("wither_skeleton", WitherSkeletonPet::new, Rarity.EPIC, Rarity.LEGENDARY);

            // Register foraging pets
            petRegistry.registerPet("giraffe", GiraffePet::new, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("monkey", MonkeyPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("ocelot", OcelotPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);

            // Register alchemy pets
            petRegistry.registerPet("parrot", ParrotPet::new, Rarity.EPIC, Rarity.LEGENDARY);
            petRegistry.registerPet("sheep", SheepPet::new, Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);

            // Register enchanting pets
            petRegistry.registerPet("guardian", GuardianPet::new, Rarity.RARE, Rarity.EPIC, Rarity.LEGENDARY);


            Messages.debug("Successfully registered " + petRegistry.getRegisteredCount() + " pet types");

            Messages.debug("Registered pets:");
            for (String petId : petRegistry.getRegisteredPetIds()) {
                Rarity[] rarities = petRegistry.getAvailableRarities(petId);
                Messages.debug("- " + petId + " (rarities: " +
                        String.join(", ", java.util.Arrays.stream(rarities)
                                .map(Enum::name).toArray(String[]::new)) + ")");
            }
        } catch (Exception e) {
            Messages.debug("Failed to initialize pets: " + e.getMessage());
        }
    }

    public Pet getPet(String petId) {
        return petRegistry.createPet(petId);
    }

    public Pet getPet(String petId, Rarity rarity) {
        return petRegistry.createPet(petId, rarity);
    }

    public Set<String> getRegisteredPetIds() {
        return petRegistry.getRegisteredPetIds();
    }

    public Rarity[] getAvailableRarities(String petId) {
        return petRegistry.getAvailableRarities(petId);
    }

    public boolean isPetRegistered(String petId) {
        return petRegistry.isRegistered(petId);
    }

    public Map<String, Pet> getLoadedPets() {
        Map<String, Pet> loadedPets = new java.util.HashMap<>();

        for (String petId : petRegistry.getRegisteredPetIds()) {
            Rarity[] availableRarities = petRegistry.getAvailableRarities(petId);
            if (availableRarities != null) {
                for (Rarity rarity : availableRarities) {
                    Pet pet = petRegistry.createPet(petId, rarity);
                    if (pet != null) {
                        loadedPets.put(pet.getId(), pet);
                    }
                }
            }
        }

        return loadedPets;
    }

    public PetRegistry getRegistry() {
        return petRegistry;
    }

    public void reloadPets() {
        Messages.debug("Reloading pet registry...");
        petRegistry.clearRegistry();
        initializePets();
        Messages.debug("Pet registry reloaded with " + petRegistry.getRegisteredCount() + " pet types");
    }

    public String getRegistryInfo() {
        int totalPets = petRegistry.getRegisteredCount();
        int totalVariants = getLoadedPets().size();

        return String.format("Registry Info: %d pet types, %d total variants",
                totalPets, totalVariants);
    }
}
