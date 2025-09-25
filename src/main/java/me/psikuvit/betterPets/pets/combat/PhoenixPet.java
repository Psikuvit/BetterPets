package me.psikuvit.betterPets.pets.combat;

import me.psikuvit.betterPets.abilities.AbilityBuilder;
import me.psikuvit.betterPets.abilities.PetAbility;
import me.psikuvit.betterPets.abilities.phoenix.EternalCoins;
import me.psikuvit.betterPets.abilities.phoenix.FourthFlare;
import me.psikuvit.betterPets.abilities.phoenix.MagicBird;
import me.psikuvit.betterPets.abilities.phoenix.Rebirth;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.pet.PetAttribute;
import me.psikuvit.betterPets.pet.PetUpgrade;
import me.psikuvit.betterPets.utils.enums.PetType;
import me.psikuvit.betterPets.utils.enums.Rarity;
import me.psikuvit.betterPets.utils.enums.Stats;

import java.util.ArrayList;
import java.util.List;

public class PhoenixPet extends Pet {

    private static final String PET_ID = "phoenix";
    private static final String PET_NAME = "Phoenix";
    private static final String TEXTURE_URL = "https://textures.minecraft.net/texture/23aaf7b1a778949696cb99d4f04ad1aa518ceee256c72e5ed65bfa5c2d88d9e";

    public PhoenixPet(Rarity rarity) {
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
                .name("Rekindle")
                .description("Before death, become <yellow>immune</yellow> and gain <red><stat>",
                        "<red>Strength</red> for <green>2s</green>.",
                        "<dark_gray>60s cooldown</dark_gray>")
                .implementation(new Rebirth()).build());

        abilities.add(new AbilityBuilder()
                .name("Fourth Flare")
                .description("On 4th melee strike, <gold>ignite</gold> mobs dealing <red><stat>x",
                        "your <blue>Crit Damage</blue> each second for <green>2s.")
                .implementation(new FourthFlare()).build());

        if (getRarity().ordinal() >= Rarity.LEGENDARY.ordinal()) {
            abilities.add(new AbilityBuilder()
                    .name("Magic Bird")
                    .description("You may always fly on your private island and garden")
                    .implementation(new MagicBird()).build());

            abilities.add(new AbilityBuilder()
                    .name("Eternal Coins")
                    .description("Don't lose coins from death.")
                    .implementation(new EternalCoins()).build());
        }

        return abilities;
    }

    @Override
    public PetAttribute[] initializeAttributes() {
        List<PetAttribute> attributes = new ArrayList<>();

        attributes.add(new PetAttribute(10.5, 0.5, Stats.STRENGTH));
        attributes.add(new PetAttribute(51, 1, Stats.INTELLIGENCE));

        return attributes.toArray(new PetAttribute[0]);
    }

    @Override
    protected PetUpgrade initializeUpgrade() {
        return null;
    }
}
