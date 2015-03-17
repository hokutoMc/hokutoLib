package com.github.hokutomc.lib.block;

import com.github.hokutomc.lib.reflect.HT_Reflections;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by user on 2014/10/11.
 */
public abstract class HT_ContainerBlock<B extends HT_ContainerBlock<B, T>, T extends TileEntity> extends HT_Block<B> implements ITileEntityProvider {
    public HT_ContainerBlock (String modid, Material material, String innerName) {
        super(modid, material, innerName);
        this.isBlockContainer = true;
    }

    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public boolean onBlockEventReceived (World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(eventID, eventParam);
    }

    @SuppressWarnings("unchecked")
    protected T getTileEntityAt (IBlockAccess world, BlockPos pos, T... empty) {
        TileEntity te = world.getTileEntity(pos);
        return HT_Reflections.getClass(empty).isInstance(te) ? (T) te : null;
    }
}
