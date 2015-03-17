package com.github.hokutomc.lib.tileentity;

import com.github.hokutomc.lib.process.HT_ItemStackProcess;
import com.github.hokutomc.lib.process.HT_ItemStackRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

/**
 * This class allows you to create TileEntity to do some process.
 *
 * 2014/11/08.
 */
public abstract class HT_ProcessTile<T extends HT_ItemStackRecipe> extends HT_StorageTile implements IUpdatePlayerListBox {

    private HT_ItemStackProcess<T> m_process;

    public HT_ProcessTile (HT_ItemStackProcess<T> process) {
        this.m_process = process;
        this.m_process.HT_setInventory(this);
    }

    @Override
    public void updateContainingBlockInfo () {
        super.updateContainingBlockInfo();
    }

    @Override
    public void update () {
        m_process.HT_updateProcess();
    }

    @Override
    public void writeToNBT (NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        this.m_process.HT_writeToNBT(nbtTagCompound);
    }

    @Override
    public void readFromNBT (NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.m_process.HT_readFromNBT(nbtTagCompound);
    }
}
