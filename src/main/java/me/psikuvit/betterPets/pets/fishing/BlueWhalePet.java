package me.psikuvit.betterPets.pets.fishing;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.blue_whale.Archimedes;
import me.psikuvit.betterPets.abilities.blue_whale.Bulk;
import me.psikuvit.betterPets.abilities.blue_whale.Ingest;
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

public class BlueWhalePet extends Pet {

    private static final String PET_ID = "blue_whale";
    private static final String PET_NAME = "Blue Whale";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/dab779bbccc849f88273d844e8ca2f3a67a1699cb216c0a11b44326ce2cc20";

    public BlueWhalePet(Rarity rarity) {
        super(PET_ID + "_" + rarity.name().toLowerCase(),
                PET_NAME,
                PetType.FISHING,
                rarity,
                TEXTURE_URL);
    }

    @Override
    protected List<PetAbility> initializeAbilities() {
        List<PetAbility> abilities = new ArrayList<>();

        abilities.add(new AbilityBuilder()
                .name("Ingest")
                .description("All potions heal <red>+<stat></red>.")
                .implementation(new Ingest())
                .build());

        if (getRarity().ordinal() >= Rarity.RARE.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Bulk")
                    .description("Gain <green>+<stat> Defense</green> per <red>20 Max ❤ Health.")
                    .implementation(new Bulk())
                    .build());
        }

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Archimedes")
                    .description("Gain <red>+<stat>% Max Health.")
                    .implementation(new Archimedes())
                    .build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        attributes.add(new PetAttribute(2, 2, Stats.HEALTH));

        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        Map<Material, Integer> upgradeMaterials = new HashMap<>();

        return switch (getRarity()) {
            case RARE -> {
                upgradeMaterials.put(Material.PRISMARINE_CRYSTALS, 32);
                upgradeMaterials.put(Material.HEART_OF_THE_SEA, 1);
                yield new PetUpgrade(Rarity.EPIC, 5000, 3 * 60 * 60 * 1000L, upgradeMaterials);
            }
            case EPIC -> {
                upgradeMaterials.put(Material.NAUTILUS_SHELL, 16);
                upgradeMaterials.put(Material.CONDUIT, 1);
                yield new PetUpgrade(Rarity.LEGENDARY, 10000, 6 * 60 * 60 * 1000L, upgradeMaterials);
            }
            default -> null;
        };
    }
}