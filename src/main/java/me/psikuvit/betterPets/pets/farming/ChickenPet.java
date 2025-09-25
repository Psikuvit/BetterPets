package me.psikuvit.betterPets.pets.farming;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.chicken.EggstraLoot;
import me.psikuvit.betterPets.abilities.chicken.FreeRange;
import me.psikuvit.betterPets.abilities.chicken.LightFeet;
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

public class ChickenPet extends Pet {

    private static final String PET_ID = "chicken";
    private static final String PET_NAME = "Chicken";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893";

    public ChickenPet(Rarity rarity) {
        super(PET_ID + "_" + rarity.name().toLowerCase(),
                PET_NAME,
                PetType.FARMING,
                rarity,
                TEXTURE_URL);
    }

    @Override
    protected List<PetAbility> initializeAbilities() {
        List<PetAbility> abilities = new ArrayList<>();

        abilities.add(new AbilityBuilder()
                .name("Free Range")
                .description("Grants <gold>+<stat> Farming Fortune</gold> while",
                        "on Public Islands")
                .implementation(new FreeRange()).build());

        if (getRarity().ordinal() >= Rarity.RARE.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Eggstra Loot")
                    .description("Chickens always drop an Egg when",
                            "killed. Grants a <green><stat>%</green> chance for animals",
                            "to drop an additional item")
                    .implementation(new EggstraLoot()).build());
        }

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Light Feet")
                    .description("Reduces fall damage by <green><stat>%")
                    .implementation(new LightFeet()).build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        attributes.add(new PetAttribute(0.5, 0.5, Stats.SPEED));
        attributes.add(new PetAttribute(0.5, 0.5, Stats.FARMING_FORTUNE));

        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        Map<Material, Integer> upgradeMaterials = new HashMap<>();

        return switch (getRarity()) {
            case COMMON -> {
                upgradeMaterials.put(Material.EGG, 64);
                upgradeMaterials.put(Material.FEATHER, 32);
                yield new PetUpgrade(Rarity.UNCOMMON, 400, 15 * 60 * 1000L, upgradeMaterials);
            }
            case UNCOMMON -> {
                upgradeMaterials.put(Material.COOKED_CHICKEN, 32);
                upgradeMaterials.put(Material.WHEAT_SEEDS, 64);
                yield new PetUpgrade(Rarity.RARE, 800, 30 * 60 * 1000L, upgradeMaterials);
            }
            case RARE -> {
                upgradeMaterials.put(Material.GOLDEN_CARROT, 16);
                upgradeMaterials.put(Material.EGG, 128);
                yield new PetUpgrade(Rarity.EPIC, 1600, 60 * 60 * 1000L, upgradeMaterials);
            }
            case EPIC -> {
                upgradeMaterials.put(Material.ENCHANTED_GOLDEN_APPLE, 1);
                upgradeMaterials.put(Material.FEATHER, 128);
                yield new PetUpgrade(Rarity.LEGENDARY, 3200, 2 * 60 * 60 * 1000L, upgradeMaterials);
            }
            default -> null;
        };
    }
}
