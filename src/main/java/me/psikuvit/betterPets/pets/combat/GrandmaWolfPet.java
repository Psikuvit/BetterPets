package me.psikuvit.betterPets.pets.combat;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.grandma_wolf.KillCombo;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.pet.PetAttribute;
import me.psikuvit.betterPets.pet.PetUpgrade;
import me.psikuvit.betterPets.utils.enums.PetType;
import me.psikuvit.betterPets.utils.enums.Rarity;
import me.psikuvit.betterPets.utils.enums.Stats;

import java.util.ArrayList;
import java.util.List;

public class GrandmaWolfPet extends Pet {

    private static final String PET_ID = "grandma_wolf";
    private static final String PET_NAME = "Grandma Wolf";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/dc3dd984bb659849bd52994046964c22725f717e986b12d548fd169367e5c";

    public GrandmaWolfPet(Rarity rarity) {
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
                .name("Kill Combo")
                .description(List.of("Gain buffs for combo kills. Effects stack as you",
                        "increase your combo.",
                        "5 Combo (lasts <stat>s)",
                        "+3% Magic Find",
                        "10 Combo (lasts <stat>s)",
                        "+10 coins per kill",
                        "15 Combo (lasts <stat>s)",
                        "+3% Magic Find",
                        "20 Combo (lasts <stat>s)",
                        "+15 Combat Wisdom",
                        "25 Combo (lasts <stat>s)",
                        "+3% Magic Find",
                        "30 Combo (lasts <stat>s)",
                        "+20 coins per kill",
                        "The pet's perk are active even when the pet in not",
                        "equipped."
                ))
                .implementation(new KillCombo())
                .build());

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        attributes.add(new PetAttribute(1, 1, Stats.STRENGTH));
        attributes.add(new PetAttribute(0.25, 0.25, Stats.HEALTH));

        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        return null;
    }
}
