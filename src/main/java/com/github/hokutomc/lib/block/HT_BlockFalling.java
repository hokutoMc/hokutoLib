package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by user on 2014/11/03.
 */
public class HT_BlockFalling extends HT_Block<HT_BlockFalling> {
    public HT_BlockFalling (String modid, Material material, String innerName) {
        super(modid, material, innerName);
    }

    @Override
    public void HT_onBlockAdded (World world, int x, int y, int z) {
        world.scheduleBlockUpdate(x, y, z, this, this.HT_tickRate(world));
    }

    @Override
    public void HT_onNeighborBlockChange (World world, int x, int y, int z, Block block) {
        world.scheduleBlockUpdate(x, y, z, this, this.HT_tickRate(world));
    }

    @Override
    public int HT_tickRate (World world) {
        return 2;
    }

    @Override
    public void HT_updateTick (World world, int x, int y, int z, Random random) {
        if (!world.isRemote) this.HT_fall(world, x, y, z);
    }
}
