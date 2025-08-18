package me.psikuvit.betterPets.pet;

import org.bukkit.entity.EntityType;

public interface Mountable {

    EntityType getMountType();

    boolean isMountable();


}

