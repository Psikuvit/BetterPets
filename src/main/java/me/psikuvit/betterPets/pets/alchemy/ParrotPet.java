package me.psikuvit.betterPets.pets.alchemy;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.parrot.BirdDiscourse;
import me.psikuvit.betterPets.abilities.parrot.Flamboyant;
import me.psikuvit.betterPets.abilities.parrot.Repeat;
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

public class ParrotPet extends Pet {

    private static final String PET_ID = "parrot";
    private static final String PET_NAME = "Parrot";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/a4ba8d66fecb1992e94b8687d6ab4a5320ab7594ac194a2615ed4df818edbc3";

    public ParrotPet(Rarity rarity) {
        super(PET_ID + "_" + rarity.name().toLowerCase(),
                PET_NAME,
                PetType.ALCHEMY,
                rarity,
                TEXTURE_URL);
    }

    @Override
    protected List<PetAbility> initializeAbilities() {
        List<PetAbility> abilities = new ArrayList<>();


        AbilityBuilder flamboyantBuilder = new AbilityBuilder()
                .name("Flamboyant")
                .description(List.of(
                        "Adds <green><stat></green> levels to intimidation",
                        "accessories"))
                .implementation(new Flamboyant());

        AbilityBuilder repeatBuilder = new AbilityBuilder()
                .name("Repeat")
                .description(List.of("Boosts potions duration by <green><stat>%"))
                .implementation(new Repeat());

        abilities.add(flamboyantBuilder.build());
        abilities.add(repeatBuilder.build());

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {

            AbilityBuilder birdDiscourseBuilder = new AbilityBuilder()
                    .name("Bird Discourse")
                    .description(List.of(
                            "Gives <red>+<stat> Strength</red> to players",
                            "within <green>20</green> blocks.",
                            "<gray>Doesn't stack"))
                    .implementation(new BirdDiscourse());

            abilities.add(birdDiscourseBuilder.build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        switch (getRarity()) {
            case LEGENDARY, EPIC -> {
                attributes.add(new PetAttribute(1, 1, Stats.INTELLIGENCE));
                attributes.add(new PetAttribute(0.1, 0.1, Stats.CRIT_DAMAGE));
            }
        }

        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        if (getRarity() == Rarity.EPIC) {
            Map<Material, Integer> upgradeMaterials = new HashMap<>();

            upgradeMaterials.put(Material.FEATHER, 16);
            return new PetUpgrade(Rarity.LEGENDARY, 3200, 2 * 60 * 60 * 1000L, upgradeMaterials);
        } else return null;
    }
}
