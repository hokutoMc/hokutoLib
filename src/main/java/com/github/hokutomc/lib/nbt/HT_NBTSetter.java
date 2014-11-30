package com.github.hokutomc.lib.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by user on 2014/10/08.
 */
public interface HT_NBTSetter {
    void accept (ItemStack itemStack, NBTTagCompound tagCompound);
}
