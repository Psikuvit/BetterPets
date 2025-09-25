package me.psikuvit.betterPets.pets.combat;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.hound.Finder;
import me.psikuvit.betterPets.abilities.hound.PackSlayer;
import me.psikuvit.betterPets.abilities.hound.Scavenger;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.pet.PetAttribute;
import me.psikuvit.betterPets.pet.PetUpgrade;
import me.psikuvit.betterPets.utils.enums.PetType;
import me.psikuvit.betterPets.utils.enums.Rarity;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoundPet extends Pet {

    private static final String PET_ID = "hound";
    private static final String PET_NAME = "Hound";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/b7c8bef6beb77e29af8627ecdc38d86aa2fea7ccd163dc73c00f9f258f9a1457";

    public HoundPet(Rarity rarity) {
        super(PET_ID + "_" + rarity.name().toLowerCase(),
                PET_NAME,
                PetType.COMBAT,
                rarity,
                TEXTURE_URL);
    }

    @Override
    protected List<PetAbility> initializeAbilities() {
        List<PetAbility> abilities = new ArrayList<>();

        abilities.add(new AbilityBuilder()
                .name("Scavenger")
                .description("Gain <green>+<stat></green> coins per monster kill")
                .implementation(new Scavenger()).build());

        abilities.add(new AbilityBuilder()
                .name("Finder")
                .description("Increases the chance for monsters to drop their",
                        "armor by <green><stat>%</green>.")
                .implementation(new Finder()).build());

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Pack Slayer")
                    .description("Gain <aqua><stat>x</aqua> Combat XP against Wolves")
                    .implementation(new PackSlayer()).build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        attributes.add(new PetAttribute(0.1, 0.1, Stats.SPEED));
        attributes.add(new PetAttribute(0.4, 0.4, Stats.STRENGTH));
        attributes.add(new PetAttribute(0.25, 0.25, Stats.ATTACK_SPEED));
        attributes.add(new PetAttribute(0.05, 0.05, Stats.FEROCITY));
        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        Map<Material, Integer> upgradeMaterials = new HashMap<>();

        return switch (getRarity()) {
            case EPIC -> {
                upgradeMaterials.put(Material.BONE, 128);
                upgradeMaterials.put(Material.ROTTEN_FLESH, 64);
                yield new PetUpgrade(Rarity.LEGENDARY, 7500, 10 * 24 * 60 * 60 * 1000L, upgradeMaterials);
            }
            default -> null;
        };
    }
}
