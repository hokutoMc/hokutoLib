package com.github.hokutomc.lib.world.gen;

import com.github.hokutomc.lib.HT_Registries;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by user on 2015/03/09.
 */
public class HT_OreGenerator implements IWorldGenerator {

    @Override
    public void generate (Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider instanceof WorldProviderSurface) {
            this.generateSurface(world, random, chunkX << 4, chunkZ << 4);
        }

        if (world.provider instanceof WorldProviderEnd) {
            this.generateEnd(world, random, chunkX << 4, chunkZ << 4);
        }

        if (world.provider instanceof WorldProviderHell) {
            this.generateHell(world, random, chunkX << 4, chunkZ << 4);
        }
    }


    public BlockPos[] getRandomPosArrayInChunk (Random random, int repeatTimes, int x, int z, int maxY, int minY) {
        assert minY > 0;
        assert maxY < 256;
        assert minY < maxY;
        BlockPos[] poses = new BlockPos[repeatTimes];
        for (int i = 0; i < repeatTimes; i++) {
            int genX = x + random.nextInt(16);
            int genY = minY + random.nextInt(maxY - minY);
            int genZ = z + random.nextInt(16);
            poses[i] = new BlockPos(genX, genY, genZ);
        }
        return poses;
    }

    protected void generateSurface (World world, Random random, int x, int z) {
    }

    protected void generateEnd (World world, Random random, int x, int z) {
    }

    protected void generateHell (World world, Random random, int x, int z) {
    }

    public void register (int weight) {
        HT_Registries.registerWorldGenerator(this, weight);
    }
}
