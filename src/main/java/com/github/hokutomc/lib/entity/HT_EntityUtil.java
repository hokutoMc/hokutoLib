package com.github.hokutomc.lib.entity;

import net.minecraft.entity.Entity;

import static java.lang.Math.sqrt;

/**
 * 2014/10/26.
 */
public final class HT_EntityUtil {
    private HT_EntityUtil () {
    }

    public static float getReachingSpeed (Entity attacker, Entity target) {
        float distance = attacker.getDistanceToEntity(target);
        float lastDist = (float) sqrt(square(attacker.lastTickPosX - target.lastTickPosX)
                + square(attacker.lastTickPosY - target.lastTickPosY)
                + square(attacker.lastTickPosZ - target.lastTickPosZ));
        return lastDist - distance;
    }

    private static float square (double value) {
        return (float) Math.pow(value, 2.0);
    }
}
