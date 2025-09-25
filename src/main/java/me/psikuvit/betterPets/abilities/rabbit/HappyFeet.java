package me.psikuvit.betterPets.abilities.rabbit;

import com.willfp.ecoskills.api.modifiers.StatModifier;
import me.psikuvit.betterPets.abilities.AbilityStats;
import me.psikuvit.betterPets.abilities.IAbility;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.utils.enums.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HappyFeet implements IAbility {

    private final Map<UUID, StatModifier> activeSpeedModifiers = new HashMap<>();

    @Override
    public void onEquip(Player owner) {
        if (owner.hasPotionEffect(PotionEffectType.JUMP_BOOST)) {
            Pet pet = playerPetManager.getActivePet(owner);
            if (pet != null) {
                applyAbilityStats(owner);
            }
        }
    }

    @Override
    public void onUnequip(Player owner) {
        removeAbilityStats(owner);
    }

    @Override
    public void handleEvent(Event event, Player owner) {
        if (!(event instanceof EntityPotionEffectEvent potionEvent)) return;
        if (!potionEvent.getEntity().equals(owner)) return;

        Pet pet = playerPetManager.getActivePet(owner);
        if (pet == null) return;

        PotionEffect newEffect = potionEvent.getNewEffect();
        PotionEffect oldEffect = potionEvent.getOldEffect();

        if (potionEvent.getAction() == EntityPotionEffectEvent.Action.ADDED) {
            if (newEffect != null && newEffect.getType() == PotionEffectType.JUMP_BOOST) {
                applyAbilityStats(owner);
            }
        } else if (potionEvent.getAction() == EntityPotionEffectEvent.Action.REMOVED) {
            if (oldEffect != null && oldEffect.getType() == PotionEffectType.JUMP_BOOST) {
                removeAbilityStats(owner);
            }
        } else if (potionEvent.getAction() == EntityPotionEffectEvent.Action.CHANGED) {
            if (newEffect != null && newEffect.getType() == PotionEffectType.JUMP_BOOST) {
                removeAbilityStats(owner);
                applyAbilityStats(owner);
            }
        }
    }

    @Override
    public AbilityStats getAbilityStat() {
        return new AbilityStats().addStatAmplifier(Stats.SPEED, 0.5, 0.5);
    }
}
