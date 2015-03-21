package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by user on 2014/11/03.
 */
public class HT_BlockFalling<T extends HT_BlockFalling<T>> extends HT_Block<T> {
    public static class Raw extends HT_BlockFalling<Raw> {
        public Raw (String modid, Material sand, String name) {
            super(modid, sand, name);
        }
    }

    public HT_BlockFalling (String modid, Material material, String innerName) {
        super(modid, material, innerName);
    }

    @Override
    public void onBlockAdded (World world, BlockPos pos, IBlockState state) {
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    @Override
    public void onNeighborBlockChange (World world, BlockPos pos, IBlockState state, Block neighbor) {
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    @Override
    public int tickRate (World world) {
        return 2;
    }

    @Override
    public void updateTick (World world, BlockPos pos, IBlockState state, Random random) {
        if (!world.isRemote) this.fall(world, pos);
    }


}
