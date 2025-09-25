package me.psikuvit.betterPets.pets.combat;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.horse.Ridable;
import me.psikuvit.betterPets.abilities.rock.Fortify;
import me.psikuvit.betterPets.abilities.rock.SailingStone;
import me.psikuvit.betterPets.abilities.rock.SteadyGround;
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

public class RockPet extends Pet {

    private static final String PET_ID = "rock";
    private static final String PET_NAME = "Rock";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/cb2b5d48e57577563aca31735519cb622219bc058b1f34648b67b8e71bc0fa";

    public RockPet(Rarity rarity) {
        super(PET_ID + "_" + rarity.name().toLowerCase(),
                PET_NAME,
                PetType.MINING,
                rarity,
                TEXTURE_URL);
    }

    @Override
    protected List<PetAbility> initializeAbilities() {
        List<PetAbility> abilities = new ArrayList<>();

        abilities.add(new AbilityBuilder()
                .name("Ridable")
                .description("Right-click your summoned pet to ride it!")
                .implementation(new Ridable()).build());

        abilities.add(new AbilityBuilder()
                .name("Sailing Stone")
                .description("Sneak to move your rock to your location",
                        "(15s cooldown)")
                .implementation(new SailingStone()).build());

        if (getRarity().ordinal() >= Rarity.RARE.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Fortify")
                    .description("While sitting on your rock, gain <green>+<stat>%</green> defense")
                    .implementation(new Fortify()).build());
        }

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Steady Ground")
                    .description("While sitting on your rock, gain <red>+<stat>X</red> damage")
                    .implementation(new SteadyGround()).build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        attributes.add(new PetAttribute(2, 2, Stats.DEFENSE));

        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        Map<Material, Integer> upgradeMaterials = new HashMap<>();

        return switch (getRarity()) {
            case LEGENDARY -> {
                upgradeMaterials.put(Material.DIAMOND_BLOCK, 16);
                upgradeMaterials.put(Material.EMERALD_BLOCK, 8);
                yield new PetUpgrade(Rarity.MYTHIC, 50000, 24 * 60 * 60 * 1000L, upgradeMaterials);
            }
            default -> null;
        };
    }
}
