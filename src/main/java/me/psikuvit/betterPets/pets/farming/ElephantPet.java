package me.psikuvit.betterPets.pets.farming;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.elephant.Stomp;
import me.psikuvit.betterPets.abilities.elephant.TrunkEfficiency;
import me.psikuvit.betterPets.abilities.elephant.WalkingFortress;
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

public class ElephantPet extends Pet {

    private static final String PET_ID = "elephant";
    private static final String PET_NAME = "Elephant";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/7071a76f669db5ed6d32b48bb2dba55d5317d7f45225cb3267ec435cfa514";

    public ElephantPet(Rarity rarity) {
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
                .name("Stomp")
                .description("Gain <green><stat> Defense</green> for every <white>100 Speed</white>")
                .implementation(new Stomp())
                .build());

        if (getRarity().ordinal() >= Rarity.RARE.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Walking Fortress")
                    .description("Gain <red><stat><red> for every <green>10 Defense")
                    .implementation(new WalkingFortress())
                    .build());
        }

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Trunk Efficiency")
                    .description("Grants <gold>+<stat> Farming Fortune</gold>,",
                            "which increases your chance for multiple drops")
                    .implementation(new TrunkEfficiency()).build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        attributes.add(new PetAttribute(1, 1, Stats.HEALTH));
        attributes.add(new PetAttribute(0.75, 0.75, Stats.INTELLIGENCE));

        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        Map<Material, Integer> upgradeMaterials = new HashMap<>();

        return switch (getRarity()) {
            case RARE -> {
                upgradeMaterials.put(Material.HAY_BLOCK, 32);
                upgradeMaterials.put(Material.SUGAR_CANE, 64);
                yield new PetUpgrade(Rarity.EPIC, 3000, 2 * 60 * 60 * 1000L, upgradeMaterials);
            }
            case EPIC -> {
                upgradeMaterials.put(Material.GOLDEN_APPLE, 8);
                upgradeMaterials.put(Material.HAY_BLOCK, 64);
                yield new PetUpgrade(Rarity.LEGENDARY, 6000, 4 * 60 * 60 * 1000L, upgradeMaterials);
            }
            default -> null;
        };
    }
}
