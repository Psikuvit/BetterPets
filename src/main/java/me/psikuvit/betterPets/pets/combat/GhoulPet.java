package me.psikuvit.betterPets.pets.combat;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.ghoul.ArmyOfTheDead;
import me.psikuvit.betterPets.abilities.ghoul.ReaperSoul;
import me.psikuvit.betterPets.abilities.ghoul.UndeadSlayer;
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

public class GhoulPet extends Pet {

    private static final String PET_ID = "ghoul";
    private static final String PET_NAME = "Ghoul";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/87934565bf522f6f4726cdfe127137be11d37c310db34d8c70253392b5ff5b";

    public GhoulPet(Rarity rarity) {
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
                .name("Undead Slayer")
                .description(List.of("Gain bonus Combat XP vs. Zombies: <green><stat>x</green>"))
                .implementation(new UndeadSlayer())
                .build());

        abilities.add(new AbilityBuilder()
                .name("Army of the Dead")
                .description(List.of("+2 soul capacity, <green><stat>%</green> bonus soul chance"))
                .implementation(new ArmyOfTheDead())
                .build());

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Reaper Soul")
                    .description(List.of(
                            "Reduces mob summoning cost by <green>30%</green>",
                            "Summoned mobs deal <green>+20%</green> damage",
                            "Summoned mobs gain <green>+100%</green> health"))
                    .implementation(new ReaperSoul())
                    .build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();
        attributes.add(new PetAttribute(1, 1, Stats.HEALTH));
        attributes.add(new PetAttribute(0.75, 0.75, Stats.INTELLIGENCE));
        attributes.add(new PetAttribute(0.05, 0.05, Stats.FEROCITY));
        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        Map<Material, Integer> upgradeMaterials = new HashMap<>();

        return switch (getRarity()) {
            case EPIC -> {
                upgradeMaterials.put(Material.ROTTEN_FLESH, 512); // proxy for Revenant Flesh
                yield new PetUpgrade(Rarity.LEGENDARY, 3_500_000, 10L * 24 * 60 * 60 * 1000L, upgradeMaterials);
            }
            default -> null;
        };
    }
}
