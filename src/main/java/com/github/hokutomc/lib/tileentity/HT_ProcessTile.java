package com.github.hokutomc.lib.tileentity;

import com.github.hokutomc.lib.process.HT_ItemStackRecipe;
import com.github.hokutomc.lib.process.HT_ItemStackProcess;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This class allows you to create TileEntity to do some process.
 *
 * 2014/11/08.
 */
public abstract class HT_ProcessTile<T extends HT_ItemStackRecipe> extends HT_StorageTile {

    private HT_ItemStackProcess<T> m_process;

    public HT_ProcessTile (HT_ItemStackProcess<T> process) {
        this.m_process = process;
        this.m_process.HT_setInventory(this);
    }

    @Override
    public void HT_updateEntity () {
        m_process.HT_updateProcess();
    }

    @Override
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound) {
        super.HT_writeToNBT(nbtTagCompound);
        this.m_process.HT_writeToNBT(nbtTagCompound);
    }

    @Override
    public void HT_readFromNBT (NBTTagCompound nbtTagCompound) {
        super.HT_readFromNBT(nbtTagCompound);
        this.m_process.HT_readFromNBT(nbtTagCompound);
    }
}
