package com.github.hokutomc.lib.nbt;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by user on 2014/12/10.
 */
public interface HT_I_NBTData<T> {
    void HT_writeToNBT (NBTTagCompound nbtTagCompound, Object... objects);
    T HT_readFromNBT (NBTTagCompound nbtTagCompound, Object... objects);
}
