package com.github.hokutomc.lib.tileentity;

import com.github.hokutomc.lib.process.HT_ItemStackRecipe;
import com.github.hokutomc.lib.process.HT_Process;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by user on 2014/11/08.
 */
public abstract class HT_ProcessTile<T extends HT_ItemStackRecipe> extends HT_StorageTile {

    private HT_Process<T> process;

    public HT_ProcessTile (HT_Process<T> process) {
        this.process = process;
        this.process.HT_setInventory(this);
    }

    @Override
    public void HT_updateEntity () {
        process.HT_updateProcess();
    }

    @Override
    public void HT_writeToNBT (NBTTagCompound nbtTagCompound) {
        super.HT_writeToNBT(nbtTagCompound);
        this.process.HT_writeToNBT(nbtTagCompound);
    }

    @Override
    public void HT_readFromNBT (NBTTagCompound nbtTagCompound) {
        super.HT_readFromNBT(nbtTagCompound);
        this.process.HT_readFromNBT(nbtTagCompound);
    }
}
