package com.github.hokutomc.lib.world.gen;

import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by user on 2015/03/10.
 */
public abstract class HT_OreGenGen {

    public final int yMax;
    public final int yMin;
    public final int chance;

    public HT_OreGenGen (int yMax, int yMin, int chance) {
        this.yMax = yMax;
        this.yMin = yMin;
        this.chance = chance;
    }

    public final void generateRandomPos (World world, Random random, int x, int z) {
        for (int i = 0; i < this.chance; i++) {
            int genX = x + random.nextInt(16);
            int genY = yMin + random.nextInt(yMax - yMin);
            int genZ = z + random.nextInt(16);
            gen(world, random, genX, genY, genZ);
        }
    }

    protected abstract void gen (World world, Random random, int genX, int genY, int genZ);
}
