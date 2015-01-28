package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by user on 2014/10/11.
 */
public abstract class HT_ContainerBlock<T extends TileEntity> extends HT_Block<HT_ContainerBlock> implements ITileEntityProvider {
    public HT_ContainerBlock (String modid, Material material, String innerName) {
        super(modid, material, innerName);
        this.isBlockContainer = true;
    }

    @Override
    public void HT_onBlockAdded (World world, int x, int y, int z) {
        super.HT_onBlockAdded(world, x, y, z);
    }

    @Override
    public void HT_breakBlock (World world, int x, int y, int z, Block block, int meta) {
        super.HT_breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean HT_onBlockEventReceived (World world, int x, int y, int z, int i1, int i2) {
        super.HT_onBlockEventReceived(world, x, y, z, i1, i2);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(i1, i2);
    }

    @SuppressWarnings("unchecked")
    protected T getTileEntityAt (World world, int x, int y, int z, Class<T> teClass) {
        TileEntity te = world.getTileEntity(x, y, z);
        return teClass.isInstance(te) ? (T) te : null;
    }
}
